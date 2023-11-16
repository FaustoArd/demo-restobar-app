package com.lord.arbam.services;

import java.util.List;
import java.util.Optional;

import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;

public interface RestoTableService {
	
	public RestoTable findRestoTableById(Long id);
	
	public List<RestoTable> findAllRestoTables();
	
	public RestoTable openRestoTable(RestoTable restoTable);
	
	public RestoTable closeRestoTable(Long restoTableId);
	
	public List<RestoTable> findAllByOrderByIdAsc();
	
	public RestoTable saveRestoTable(RestoTable restoTable);
	
	public RestoTable updateRestoTableTotalPrice(RestoTable restoTable,List<RestoTableOrder> orders);
	
	public Optional<RestoTable> findByTableNumber(Integer tableNumber);
	

}
