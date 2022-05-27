package com.gyarleque.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gyarleque.springboot.app.commons.models.entity.Product;

@FeignClient(name = "service-products")
public interface ProductClientRest {
	
	@GetMapping("/list")
	List<Product> list();
	
	@GetMapping("/view/{id}")
	Product detail(@PathVariable Long id);
}
