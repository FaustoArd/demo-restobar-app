package com.lord.arbam.service;

import java.util.List;
import java.util.Optional;

import com.lord.arbam.dto.OrderPaymentMethodDto;
import com.lord.arbam.dto.OrderPaymentMethodResponse;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.dto.RestoTableDto;
import com.lord.arbam.dto.RestoTableOrderDto;
import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableOrder;

public interface RestoTableService {
	
	public RestoTable findRestoTableById(Long id);
	
	public List<RestoTable> findAllRestoTables();
	
	public RestoTable openRestoTable(RestoTable restoTable);
	
	public RestoTable updateRestoTable(RestoTable restoTable);
	
	public RestoTableClosedDto  closeRestoTable(Long restoTableId,Long workingDayId,List<OrderPaymentMethodDto> orderPaymentMethodDtos);
	
	public List<RestoTable> findAllByOrderByIdAsc();
	
	public RestoTable saveRestoTable(RestoTable restoTable);
	
	public RestoTable updateRestoTableTotalPrice(RestoTable restoTable,List<RestoTableOrder> orders);
	
	public Optional<RestoTable> findByTableNumber(Integer tableNumber);
	
	public List<PaymentMethod> findAllPaymentMethods();
	
	public RestoTableDto copyRemainingTable(RestoTableDto restoTableDto);
	
	public void checkTablesOpen();
	

}
