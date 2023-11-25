package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.model.EmployeeJob;

public interface EmployeeJobService {

	public EmployeeJob findJobById(Long id);
	
	public EmployeeJob saveJob(EmployeeJob employeeJob);
	
	public List<EmployeeJob> findAllJobs();
	
	public List<EmployeeJob> findAllbyJobRoleAsc();
	
	public void deleteJobById(Long id);
}
