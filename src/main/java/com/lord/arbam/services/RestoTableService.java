package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.RestoTable;

public interface RestoTableService {
	
	public RestoTable findRestoTableById(Long id);
	
	public List<RestoTable> findAllRestoTables();
	
	public RestoTable createRestoTable(RestoTable restoTable);
	
	public RestoTable addOrderToRestotable(RestoTable restoTable);
	
	public RestoTable updateRestoTablePrice(RestoTable restoTable);
	
	public RestoTable closeRestoTable(RestoTable restoTable);
	
	public List<RestoTable> findAllByOrderByIdAsc();
	

}
