package com.gyarleque.springboot.app.item.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.gyarleque.springboot.app.commons.models.entity.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
	
	private Product product;
	private Integer count;
	
	public Double getTotal() {
		return product.getPrice() * count.doubleValue();
	}

}
