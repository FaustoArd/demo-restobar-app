package com.lord.arbam.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPaymentMethodDto {
	
	
	private long id;
	
	private PaymentMethodDto paymentMethod;
	
//	private long paymentMethodId;
	
	private RestoTableClosedDto restoTableClosedDto;
	
	private List<RestoTableOrderDto> orders;
	
	
	
	private BigDecimal paymentTotal;

}
