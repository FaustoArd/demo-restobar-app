package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.WorkingDay;

public interface WorkingDayService {
	
	public List<WorkingDay> findAll();
	
	public WorkingDay findById(Long id);
	
	public WorkingDay save(WorkingDay workingDay);
	
	public WorkingDay update(WorkingDay workingDay);

}
