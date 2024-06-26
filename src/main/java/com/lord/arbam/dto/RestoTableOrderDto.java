package com.lord.arbam.dto;

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
	
	private Long restoTableId;
	
	private BigDecimal totalOrderPrice;
	
	private boolean amount;

}
