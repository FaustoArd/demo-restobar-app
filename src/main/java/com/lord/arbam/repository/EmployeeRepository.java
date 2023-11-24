package com.lord.arbam.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> findByEmployeeJobJobRole(String jobRole);
	
	
	
	

}
