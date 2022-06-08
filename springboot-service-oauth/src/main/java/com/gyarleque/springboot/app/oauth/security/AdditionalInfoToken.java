package com.gyarleque.springboot.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.gyarleque.springboot.app.commons.users.models.entity.User;
import com.gyarleque.springboot.app.oauth.services.UserService;

@Component
public class AdditionalInfoToken implements TokenEnhancer {
	
	@Autowired
	private UserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInformation = new HashMap<>();
		
		User user = userService.findByUsername(authentication.getName());
		
		additionalInformation.put("name", user.getName());
		additionalInformation.put("lastName", user.getLastName());
		additionalInformation.put("email", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		
		return accessToken;
	}
	
	

}
