package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.dto.PaymentMethodDto;

public interface PaymentMethodService {
	
	public List<PaymentMethodDto> findAllPaymentMethods();
	
	public PaymentMethodDto updatePaymentMethod(PaymentMethodDto paymentMethodDto);
	
	

}
