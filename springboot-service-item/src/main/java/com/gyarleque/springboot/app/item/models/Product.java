package com.gyarleque.springboot.app.item.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	
	private Long id;	
	private String name;	
	private Double price;	
	private Date createAt;
	private Integer port;

}
