package com.gyarleque.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	
	@Bean(name = "clientRest")
	@LoadBalanced
	public RestTemplate registerTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
							.slidingWindowSize(10) // number of request
							.failureRateThreshold(50) // percentage of request
							.waitDurationInOpenState(Duration.ofSeconds(10L)) // Time of wait to open circuit breaker
							.permittedNumberOfCallsInHalfOpenState(5) // number calls half open state
							.slowCallRateThreshold(50) //percentage call slow
							.slowCallDurationThreshold(Duration.ofSeconds(2L))
							.build())
					//.timeLimiterConfig(TimeLimiterConfig.ofDefaults())
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build())
					.build();
		});
	}

}
