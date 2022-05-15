package com.gyarleque.springboot.app.item.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private Long id;	
	private String name;	
	private Double price;	
	private Date createAt;
	private Integer port;

}
