package com.gyarleque.springboot.app.item.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gyarleque.springboot.app.item.models.Item;
import com.gyarleque.springboot.app.item.models.Product;
import com.gyarleque.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class ItemController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@GetMapping("/list")
	public List<Item> findAll(@RequestParam(name="name", required = false) String name, 
			@RequestHeader(name = "token-request", required = false) String token) {
		System.out.println(name);
		System.out.println(token);
		return itemService.findAll();
	}
	
	@GetMapping("/view/{id}/count/{count}")
	public Item detail(@PathVariable Long id,
			@PathVariable Integer count) {
		return cbFactory.create("items")
				.run(() -> itemService.findById(id, count), 
						e -> methodAlternative(id, count, e));
	}
	
	@CircuitBreaker(name = "items", fallbackMethod = "methodAlternative")
	@GetMapping("/view2/{id}/count/{count}")
	public Item detail2(@PathVariable Long id,
			@PathVariable Integer count) {
		return itemService.findById(id, count);
	}
	
	@CircuitBreaker(name = "items", fallbackMethod = "methodAlternativeAsync")
	@TimeLimiter(name = "items")
	@GetMapping("/view3/{id}/count/{count}")
	public CompletableFuture<Item> detail3(@PathVariable Long id,
			@PathVariable Integer count) {
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, count));
	}
	
	public Item methodAlternative(Long id, Integer count, Throwable e) {
		logger.info(e.getMessage());
		
		return Item.builder()
				.product(Product.builder()
						.id(id)
						.name("Sony Camera")
						.price(500.00)
						.build())
				.count(count)
				.build();
	}
	
	public CompletableFuture<Item> methodAlternativeAsync(Long id, Integer count, Throwable e) {
		logger.info(e.getMessage());
		
		return CompletableFuture.supplyAsync(() -> Item.builder()
				.product(Product.builder()
						.id(id)
						.name("Sony Camera")
						.price(500.00)
						.build())
				.count(count)
				.build());
	}

}
