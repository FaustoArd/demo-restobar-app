package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.Employee;
import com.lord.arbam.models.WorkingDay;

public interface WorkingDayService {
	
	public List<WorkingDay> findAll();
	
	public WorkingDay findWorkingDayById(Long id);
	
	public WorkingDay startWorkingDay(WorkingDay workingDay);
	
	public WorkingDay updateWorkingDay(WorkingDay workingDay);
	
	public WorkingDay closeWorkingDay(Long workingDayId);
	
	public WorkingDay deleteWaitressById(Long waitressId,Long workingDayId);
	
	public boolean isWorkingDayStarted(Long workingDayId);
	
	public List<Employee> findCurrentWaitressSelected(Long workingDayId);
	
	

}
