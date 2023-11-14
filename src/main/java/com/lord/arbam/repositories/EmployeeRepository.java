package com.lord.arbam.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	

}
