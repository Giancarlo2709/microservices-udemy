package com.gyarleque.springboot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	@Bean(name = "clientRest")
	@LoadBalanced
	public RestTemplate registerTemplate() {
		return new RestTemplate();
	}

}
