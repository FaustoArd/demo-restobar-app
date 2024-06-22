package com.lord.arbam.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodDto {

	
	private Long id;
	

	private String paymentMethod;
	
	private BigDecimal interest;
}
