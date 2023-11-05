package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.RestoTableOrder;

public interface RestoTableOrderService {
	
	public RestoTableOrder findOrderById(Long id);
	
	public RestoTableOrder createOrder(RestoTableOrder order);
	
	public List<RestoTableOrder> findAllOrders();
	
	public void deleteOderById(Long id);
	
	List<RestoTableOrder> findByRestoTablesId(Long id);

}
