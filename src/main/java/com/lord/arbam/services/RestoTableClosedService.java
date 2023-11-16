package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.RestoTableClosed;

public interface RestoTableClosedService {
	
	public List<RestoTableClosed> findAllTablesClosed();
	
	public List<RestoTableClosed> findAllByWorkingDayId(Long id);
	
	public RestoTableClosed findTableClosedById(Long id);
	
	public RestoTableClosed saveTableClosed(RestoTableClosed restoTableClosed);
	
	public void deleteTableClosedById(Long id);

}
