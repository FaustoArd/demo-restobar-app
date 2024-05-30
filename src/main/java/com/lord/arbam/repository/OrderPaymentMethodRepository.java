package com.lord.arbam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.RestoTableClosed;

public interface OrderPaymentMethodRepository extends JpaRepository<OrderPaymentMethod, Long> {
	
//	public List<OrderPaymentMethod> findAllByIdIn(List<Long> orderPaymentMethodId);
	
	public List<OrderPaymentMethod> findAllByRestoTableClosed(RestoTableClosed restoTableClosed);
	
	public void deleteAllByRestoTableClosedIn(List<RestoTableClosed> restoTableCloseds);
	
	

}
