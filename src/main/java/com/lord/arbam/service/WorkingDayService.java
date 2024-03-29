package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.model.Employee;
import com.lord.arbam.model.WorkingDay;

public interface WorkingDayService {
	
	public List<WorkingDay> findAll();
	
	public List<WorkingDay> findAllByOrderByDateAsc();
	
	public WorkingDay findWorkingDayById(Long id);
	
	public WorkingDay startWorkingDay(WorkingDay workingDay);
	
	public WorkingDay updateWorkingDay(WorkingDay workingDay);
	
	public WorkingDay closeWorkingDay(Long workingDayId);
	
	public WorkingDay deleteEmployeesById(Long waitressId,Long workingDayId);
	
	public boolean isWorkingDayStarted(Long workingDayId);
	
	public List<Employee> findCurrentEmployeesSelected(Long workingDayId);
	
	public void deleteWorkingDayById(Long id);
	
	public List<WorkingDay> findByEmployeesId(Long employeeId);
	
	

}
