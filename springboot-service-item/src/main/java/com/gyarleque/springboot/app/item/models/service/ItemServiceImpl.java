package com.gyarleque.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gyarleque.springboot.app.item.models.Item;
import com.gyarleque.springboot.app.commons.models.entity.Product;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Item> findAll() {
		List<Product> products = Arrays.asList(
				restTemplate.getForObject("http://service-products/list", Product[].class));
		
		return products.stream().map(p -> Item.builder()
				.product(p)
				.count(1)
				.build()).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer count) {
		Map<String, String> pathVariables = new HashMap<>();
		
		pathVariables.put("id", id.toString());
		
		Product product = restTemplate.getForObject(
				"http://service-products/view/{id}", Product.class, pathVariables);
		
		return Item.builder()
				.product(product)
				.count(count)
				.build();
	}

	@Override
	public Product save(Product product) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		
		ResponseEntity<Product> response = restTemplate
				.exchange("http://service-products/create", HttpMethod.POST, body, Product.class);
		
		return response.getBody();
	}

	@Override
	public Product update(Product product, Long id) {
		Map<String, String> pathVariables = new HashMap<>();		
		pathVariables.put("id", id.toString());
		
		HttpEntity<Product> body = new HttpEntity<Product>(product);	
		ResponseEntity<Product> response = restTemplate
				.exchange("http://service-products/edit/{id}", HttpMethod.PUT, body, Product.class, pathVariables);
		
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<>();		
		pathVariables.put("id", id.toString());
		
		restTemplate.delete("http://service-products/delete/{id}", pathVariables);
	}

}
