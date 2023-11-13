package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.RestoTableClosed;

public interface RestoTableClosedService {
	
	public List<RestoTableClosed> findAllRestoTablesClosed();
	
	public RestoTableClosed findRestoTableClosedById(Long id);
	
	public RestoTableClosed saveRestoTableClosed(RestoTableClosed restoTableClosed);
	
	public void deleteRestoTableClosedById(Long id);

}
