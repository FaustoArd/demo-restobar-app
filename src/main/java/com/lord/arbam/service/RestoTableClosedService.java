package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.dto.OrderPaymentMethodResponse;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.model.RestoTableClosed;


public interface RestoTableClosedService {
	
	public List<RestoTableClosed> findAllTablesClosed();
	
	public List<RestoTableClosed> findAllByWorkingDayId(Long workingDayId);
	
	public List<RestoTableClosed> findAllByWorkingDayIdOrderByTableNumberAsc(Long workingDayId);
	
	public RestoTableClosed findTableClosedById(Long id);
	
	public RestoTableClosed saveTableClosed(RestoTableClosed restoTableClosed);
	
	public void deleteTableClosedById(Long id);
	
	public List<OrderPaymentMethodResponse> findAllPaymentsByRestoTableClosed(long restoTableClosedId);
	
//	public List<RestoTableOrderClosed> findAllOrdersClosed(long restoTableClosedId);

}
