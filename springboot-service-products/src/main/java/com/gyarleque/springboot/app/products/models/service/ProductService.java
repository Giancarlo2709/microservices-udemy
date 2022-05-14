package com.gyarleque.springboot.app.products.models.service;

import java.util.List;

import com.gyarleque.springboot.app.products.models.entity.Product;

public interface ProductService {
	
	List<Product> findAll();
	
	Product findById(Long id);

}
