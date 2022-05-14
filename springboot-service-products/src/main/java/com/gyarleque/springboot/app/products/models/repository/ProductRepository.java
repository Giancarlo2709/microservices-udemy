package com.gyarleque.springboot.app.products.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gyarleque.springboot.app.products.models.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	
	
}
