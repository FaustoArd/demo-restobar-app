package com.lord.arbam.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableClosedDto {
	
	private Long id;
	
	private Integer tableNumber;
	
	private String employeeName;
	
	private BigDecimal totalPrice;
	
	private String paymentMethod;
	
	private Long workingDayId;
	

}
