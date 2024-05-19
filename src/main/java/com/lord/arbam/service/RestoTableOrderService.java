package com.lord.arbam.service;

import java.util.List;
import com.lord.arbam.model.RestoTableOrder;

public interface RestoTableOrderService {
	
	public RestoTableOrder findOrderById(Long id);
	
	public RestoTableOrder createOrder(RestoTableOrder order);
	
	public RestoTableOrder updateOrder(RestoTableOrder existingOrder,Integer productQuantity);
	
	public List<RestoTableOrder> findAllOrders();
	
	public void deleteOderById(Long id);
	
	public RestoTableOrder updateDeletedOrderItemPrice(RestoTableOrder order);
	
	public List<RestoTableOrder> findAllByRestoTableId(Long restoTableId);
	
	

}
