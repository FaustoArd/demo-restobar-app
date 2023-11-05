package com.lord.arbam.services;

import com.lord.arbam.models.RestoTableClosed;

public interface RestoTableClosedService {
	
	public RestoTableClosed findRestoTableClosedById(Long id);
	
	public RestoTableClosed saveRestoTableClosed(RestoTableClosed restoTableClosed);
	
	public void deleteRestoTableClosedById(Long id);

}
