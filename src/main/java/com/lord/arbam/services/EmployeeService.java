package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.Employee;

public interface EmployeeService {
	
	public Employee findEmployeeById(Long id);
	
	public Employee saveEmployee(Employee employee);
	
	public List<Employee> findAllEmployees();
	
	public void deleteEmployeeById(Long id);

}
