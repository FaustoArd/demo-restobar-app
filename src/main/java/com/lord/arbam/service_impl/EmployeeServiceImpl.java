package com.lord.arbam.service_impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.repository.EmployeeJobRepository;
import com.lord.arbam.repository.EmployeeRepository;
import com.lord.arbam.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	private final EmployeeJobRepository employeeJobRepository;
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeJobServiceImpl.class);
	
	

	@Override
	public Employee findEmployeeById(Long id) {
		log.info("Find employee by id");
		return employeeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("No se encontro el empleado. EmployeeServiceImpl.findEmployeeById"));
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		log.info("Saving new employee");
		EmployeeJob empJob = employeeJobRepository.findById(employee.getEmployeeJob().getId())
				.orElseThrow(()-> new ItemNotFoundException("Employee job not found"));
		employee.setEmployeeJob(empJob);
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		log.info("Getting all employees");
		return (List<Employee>)employeeRepository.findAll();
	}

	@Override
	public void deleteEmployeeById(Long id) {
		if(employeeRepository.existsById(id)) {
			log.info("Deleting employee");
			employeeRepository.deleteById(id);
		}else {
			log.info("Cant delete, employee not found");
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

	@Override
	public List<Employee> findAllEmployeesSortByNameAsc() {
		 Sort sort = Sort.by("employeeName");
		 return (List<Employee>)employeeRepository.findAll(sort);
	}

	

	
	
}

