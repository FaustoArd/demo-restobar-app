package com.lord.arbam.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockDto {
	
	private Long id;
	
	private Integer productStock;
	
	private Long productId;
}
