package com.lord.arbam.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PriceUpdateReportDto {

	private String productName;
	
	private BigDecimal oldPrice;
	
	private BigDecimal newPrice;
	
	public PriceUpdateReportDto(String productName,BigDecimal oldPrice,BigDecimal newPrice) {
		super();
		this.productName = productName;
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
				
	}
}
