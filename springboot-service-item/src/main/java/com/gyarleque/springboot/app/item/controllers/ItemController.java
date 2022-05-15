package com.gyarleque.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gyarleque.springboot.app.item.models.Item;
import com.gyarleque.springboot.app.item.models.Product;
import com.gyarleque.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {
	
	@Autowired
	@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/list")
	public List<Item> findAll() {
		return itemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "methodAlternative")
	@GetMapping("/view/{id}/count/{count}")
	public Item detail(@PathVariable Long id,
			@PathVariable Integer count) {
		return itemService.findById(id, count);
	}
	
	public Item methodAlternative(Long id, Integer count) {
		return Item.builder()
				.product(Product.builder()
						.id(id)
						.name("Sony Camera")
						.price(500.00)
						.build())
				.count(count)
				.build();
	}

}
