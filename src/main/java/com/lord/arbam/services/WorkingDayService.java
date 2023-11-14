package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.WorkingDay;

public interface WorkingDayService {
	
	public List<WorkingDay> findAll();
	
	public WorkingDay findWorkingDayById(Long id);
	
	public WorkingDay startWorkingDay(WorkingDay workingDay);
	
	public WorkingDay updateWorkingDay(WorkingDay workingDay);
	
	public WorkingDay closeWorkingDay(WorkingDay workingDay);

}
