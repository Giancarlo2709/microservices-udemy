package com.gyarleque.springboot.app.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootServiceProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceProductsApplication.class, args);
	}

}
