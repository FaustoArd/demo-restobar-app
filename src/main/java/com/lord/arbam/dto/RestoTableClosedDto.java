package com.lord.arbam.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableClosedDto {
	
	private Long id;
	
	private Integer tableNumber;
	
	private String employeeName;
	
	private BigDecimal totalPrice;
	
	private List<OrderPaymentMethodDto> ordersPaymentMethodDtos;
	
	private Long workingDayId;
	
	
	

}
