package com.lord.arbam.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

	private Long id;

	private String productName;

	private Long categoryId;

	private String categoryName;
	
	private Integer productStock;
	
	private Double productPrice;
	
	private boolean mixed;



}
