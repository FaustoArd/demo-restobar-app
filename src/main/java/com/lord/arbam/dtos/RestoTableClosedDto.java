package com.lord.arbam.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableClosedDto {
	
	private Long id;
	
	private Integer tableNumber;
	
	private String employeeName;
	
	private Double totalPrice;
	
	private String paymentMethod;
	

}
