package com.gyarleque.springboot.app.products.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gyarleque.springboot.app.commons.models.entity.Product;
import com.gyarleque.springboot.app.products.models.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	
	@GetMapping("/list")
	public List<Product> list() {
		return productService.findAll()
				.stream()
				.map(product -> {
					product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
					// product.setPort(port);
					return product;
				})
				.collect(Collectors.toList());
	}
	
	@GetMapping("/view/{id}")
	public Product detail(@PathVariable Long id) throws InterruptedException {
		
		if (id.equals(10L)) {
			throw new IllegalStateException("Product not found");
		}
		
		if (id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		// product.setPort(port);
		
		return product;
	}

}
