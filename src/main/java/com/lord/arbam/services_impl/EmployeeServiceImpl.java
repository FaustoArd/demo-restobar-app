package com.lord.arbam.services_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.EmployeeJob;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.EmployeeJobRepository;
import com.lord.arbam.repositories.EmployeeRepository;
import com.lord.arbam.services.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	private final EmployeeJobRepository employeeJobRepository;
	
	

	@Override
	public Employee findEmployeeById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro el empleado. EmployeeServiceImpl.findEmployeeById"));
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		EmployeeJob empJob = employeeJobRepository.findById(employee.getEmployeeJob().getId())
				.orElseThrow(()-> new ItemNotFoundException("No se encontro el puesto de trabajo. EmployeeServiceImpl.saveEmployee"));
		employee.setEmployeeJob(empJob);
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return (List<Employee>)employeeRepository.findAll();
	}

	@Override
	public void deleteEmployeeById(Long id) {
		if(employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
		}else {
			throw new ItemNotFoundException("No se encontro el empleado. EmployeeServiceImpl.deleteEmployeeById");
		}
		
	}

	@Override
	public List<Employee> findAllById(List<Long> ids) {
		return (List<Employee>)employeeRepository.findAllById(ids);
	}

	@Override
	public List<Employee> findByEmployeeJobJobRole(String jobRole) {
		return (List<Employee>)employeeRepository.findByEmployeeJobJobRole(jobRole);
	}

	

	
	
}

