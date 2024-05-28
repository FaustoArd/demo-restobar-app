package com.lord.arbam.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderPaymentMethodResponse {

	private long id;
	
	private PaymentMethodDto paymentMethod;
	
	private long paymentMethodId;
	
	private RestoTableClosedDto restoTableClosedDto;
	
	private List<RestoTableOrderClosedDto> orderCloseds;
	
	private BigDecimal paymentTotal;
}
