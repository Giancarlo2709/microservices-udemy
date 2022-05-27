package com.gyarleque.springboot.app.item.models.service;

import java.util.List;

import com.gyarleque.springboot.app.item.models.Item;
import com.gyarleque.springboot.app.item.models.Product;

public interface ItemService {
	
	List<Item> findAll();
	
	Item findById(Long id, Integer count);
	
	Product save(Product product);
	
	Product update(Product product, Long id);
	
	void delete(Long id);

}
