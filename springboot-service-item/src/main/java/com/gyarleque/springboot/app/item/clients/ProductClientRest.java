package com.gyarleque.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gyarleque.springboot.app.item.models.Product;

@FeignClient(name = "service-products")
public interface ProductClientRest {
	
	@GetMapping("/list")
	List<Product> list();
	
	@GetMapping("/view/{id}")
	Product detail(@PathVariable Long id);
	
	@PostMapping("/create")
	Product create(@RequestBody Product product);
	
	@PutMapping("/edit/{id}")
	Product update(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable Long id);
}
