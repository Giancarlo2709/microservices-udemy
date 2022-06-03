package com.gyarleque.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gyarleque.springboot.app.commons.users.models.entity.User;
import com.gyarleque.springboot.app.oauth.clients.UserFeignClient;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserFeignClient userFeignClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			User user = findByUsername(username);
			
			List<GrantedAuthority> authorities = user.getRoles()
					.stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.peek(authority -> logger.info(authority.getAuthority()))
					.collect(Collectors.toList());
			
			logger.info("User Authenticated: " + username);
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
					user.isStatus(), true, true, true, authorities);
		} catch(FeignException e) {
			logger.error("Login error, user does not exist '" + username + "' in the system");
			throw new UsernameNotFoundException("Login error, user does not exist '" + username + "' in the system");
		}
	}

	@Override
	public User findByUsername(String username) {
		return userFeignClient.findByUsername(username);
	}

	@Override
	public User update(User user, Long id) {
		return userFeignClient.update(user, id);
	}

}
