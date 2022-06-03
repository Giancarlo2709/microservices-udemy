package com.gyarleque.springboot.app.gateway.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/security/oauth/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/products/list", "/api/items/list", "/api/users/users").permitAll()
			.antMatchers(HttpMethod.GET, "/api/products/view/{id}", "/api/items/view/{id}/count/{count}",
					"/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
			.antMatchers("/api/products/**", "/api/items/**", "/api/users/**").hasRole("ADMIN");
			//.antMatchers(HttpMethod.POST, "/api/products/create", "/api/items/create", "/api/users/users").hasRole("ADMIN")
			//.antMatchers(HttpMethod.PUT, "/api/products/edit/{id}", "/api/items/edit/{id}", "/api/users/users/{id}").hasRole("ADMIN")
			//.antMatchers(HttpMethod.DELETE, "/api/products/delete/{id}", "/api/items/delete/{id}", "/api/users/users/{id}").hasRole("ADMIN");
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("algun_codigo_secret_aeiou");
		return tokenConverter;
	}	

}
