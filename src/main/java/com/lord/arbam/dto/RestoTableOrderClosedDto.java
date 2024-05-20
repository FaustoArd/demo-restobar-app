package com.lord.arbam.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableOrderClosedDto {


	private Long id;
	

	private Integer productQuantity;
	
	
	private BigDecimal totalOrderPrice;
	
	
	private String productName;
	
	
	private long restoTableClosedId;
}
