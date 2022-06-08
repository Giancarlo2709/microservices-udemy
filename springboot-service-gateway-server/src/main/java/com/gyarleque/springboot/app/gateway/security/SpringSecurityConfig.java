package com.gyarleque.springboot.app.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
		return http.authorizeExchange()
				.pathMatchers("/api/security/oauth/**").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/products/list", "/api/items/list", "/api/users/users").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/products/view/{id}", "/api/items/view/{id}/count/{count}",
						"/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
				.pathMatchers("/api/products/**", "/api/items/**", "/api/users/**").hasRole("ADMIN")
				.anyExchange().authenticated()
				.and()
				.addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.csrf().disable()
				.build();
	}

}
