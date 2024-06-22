package com.lord.arbam.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.service.PaymentMethodService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	private final PaymentMethodRepository paymentMethodRepository;

	@Override
	public List<PaymentMethodDto> findAllPaymentMethods() {
		return (List<PaymentMethodDto>) paymentMethodRepository.findAll().stream().map(payment -> {
			PaymentMethodDto dto = new PaymentMethodDto();
			dto.setId(payment.getId());
			dto.setPaymentMethod(payment.getPaymentMethod());
			dto.setInterest(payment.getInterest());
			return dto;
		}).toList();
	}

	@Override
	public PaymentMethodDto updatePaymentMethod(PaymentMethodDto paymentMethodDto) {
		PaymentMethod updatePayment = paymentMethodRepository.findById(paymentMethodDto.getId())
				.map(payment -> {
					payment.setInterest(paymentMethodDto.getInterest());
				return paymentMethodRepository.save(payment);
				}).orElseThrow(()-> new ItemNotFoundException("No se encontro el metodo de pago."));
		
		return mapToDto(updatePayment);
	}
	
	private static PaymentMethodDto mapToDto(PaymentMethod paymentMethod) {
		PaymentMethodDto dto = new PaymentMethodDto();
		dto.setId(paymentMethod.getId());
		dto.setPaymentMethod(paymentMethod.getPaymentMethod());
		dto.setInterest(paymentMethod.getInterest());
		return dto;
	}
	
	
	
}
