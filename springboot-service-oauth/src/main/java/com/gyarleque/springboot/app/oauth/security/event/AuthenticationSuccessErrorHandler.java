package com.gyarleque.springboot.app.oauth.security.event;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gyarleque.springboot.app.commons.users.models.entity.User;
import com.gyarleque.springboot.app.oauth.services.UserService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Tracer tracer;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if(authentication.getName().equalsIgnoreCase("frontendapp")) {
			return;
		}
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		logger.info("Success Login: " + user.getUsername());
		
		User userDb = userService.findByUsername(user.getUsername());
		
		if (Objects.nonNull(userDb.getRetry()) && userDb.getRetry() > 0) {
			userDb.setRetry(0);
		}
		
		userService.update(userDb, userDb.getId());		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String message = "Login Error: " + exception.getMessage();
		logger.error(message);
		
		String username = authentication.getName();
		
		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(message);
			
			User user = userService.findByUsername(username);
			
			if(Objects.isNull(user.getRetry())) {
				user.setRetry(1);
			}
			
			logger.info("Retry Actual: " + user.getRetry());
			
			user.setRetry(user.getRetry() + 1);
			
			logger.info("Retry after: " + user.getRetry());
			
			errors.append(" - Retry after Login: " + user.getRetry());
			
			if (user.getRetry() >= 3) {
				String errorMaxRetries = String.format("The user %s disabled for maximum attempts", username);
				logger.info(errorMaxRetries);
				errors.append(" - ").append(errorMaxRetries);
				user.setStatus(false);
			}
			
			userService.update(user, user.getId());
			
			tracer.currentSpan().tag("error.message", errors.toString());
			
		} catch(FeignException e) {
			logger.error(String.format("User %s not found", username));
		}		
	}
}
