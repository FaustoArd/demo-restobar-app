package com.lord.arbam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
	
	public Optional<PaymentMethod> findByPaymentMethod(String paymentMethod);

}
