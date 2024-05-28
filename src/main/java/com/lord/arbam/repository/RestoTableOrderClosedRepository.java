package com.lord.arbam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;

public interface RestoTableOrderClosedRepository extends JpaRepository<RestoTableOrderClosed, Long> {

//	public List<RestoTableOrderClosed> findAllByRestoTableClosed(RestoTableClosed restoTableClosed);
	
	public List<RestoTableOrderClosed> findAllByOrderPaymentMethod(OrderPaymentMethod orderPaymentMethod);
	
	
}
