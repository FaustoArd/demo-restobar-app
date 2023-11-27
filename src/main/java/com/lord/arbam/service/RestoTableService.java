package com.lord.arbam.service;

import java.util.List;
import java.util.Optional;

import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableOrder;

public interface RestoTableService {
	
	public RestoTable findRestoTableById(Long id);
	
	public List<RestoTable> findAllRestoTables();
	
	public RestoTable openRestoTable(RestoTable restoTable);
	
	public RestoTable closeRestoTable(Long restoTableId,Long workingDayId,PaymentMethod paymentMethod);
	
	public List<RestoTable> findAllByOrderByIdAsc();
	
	public RestoTable saveRestoTable(RestoTable restoTable);
	
	public RestoTable updateRestoTableTotalPrice(RestoTable restoTable,List<RestoTableOrder> orders);
	
	public Optional<RestoTable> findByTableNumber(Integer tableNumber);
	
	public List<PaymentMethod> findAllPaymentMethods();
	
	public void checkTablesOpen();
	

}
