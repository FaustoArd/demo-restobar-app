package com.lord.arbam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMixDto {

	private Long id;
	
	private Long productId;
	
	private String productName;
	
	private Long ingredientId;
	
	private String ingredientName;
	
	private Integer ingredientAmount;
	
	
}
