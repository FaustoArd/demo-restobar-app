package com.lord.arbam.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableOrderDto {
	
	private Long id;
	
	private Long productId;
	
	private String productName;
	
	private Integer productQuantity;
	
	private BigDecimal totalOrderPrice;
	
	

}
