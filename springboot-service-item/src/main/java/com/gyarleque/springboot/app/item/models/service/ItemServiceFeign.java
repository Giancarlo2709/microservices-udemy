package com.gyarleque.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gyarleque.springboot.app.item.clients.ProductClientRest;
import com.gyarleque.springboot.app.item.models.Item;
import com.gyarleque.springboot.app.commons.models.entity.Product;

@Service("serviceFeign")
// @Primary
public class ItemServiceFeign implements ItemService {
	
	@Autowired
	private ProductClientRest clientFeign;

	@Override
	public List<Item> findAll() {
		return clientFeign.list()
				.stream().map(p -> Item.builder()
				.product(p)
				.count(1)
				.build()).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer count) {
		return Item.builder()
				.product(clientFeign.detail(id))
				.count(count)
				.build();
	}

	@Override
	public Product save(Product product) {
		return clientFeign.create(product);
	}

	@Override
	public Product update(Product product, Long id) {
		return clientFeign.update(product, id);
	}

	@Override
	public void delete(Long id) {
		clientFeign.delete(id);		
	}
	
	

}
