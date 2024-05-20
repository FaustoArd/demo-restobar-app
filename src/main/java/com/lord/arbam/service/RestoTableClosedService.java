package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;

public interface RestoTableClosedService {
	
	public List<RestoTableClosed> findAllTablesClosed();
	
	public List<RestoTableClosed> findAllByWorkingDayId(Long workingDayId);
	
	public List<RestoTableClosed> findAllByWorkingDayIdOrderByTableNumberAsc(Long workingDayId);
	
	public RestoTableClosed findTableClosedById(Long id);
	
	public RestoTableClosed saveTableClosed(RestoTableClosed restoTableClosed);
	
	public void deleteTableClosedById(Long id);
	
	public List<RestoTableOrderClosed> findAllOrdersClosed(long restoTableClosedId);

}
