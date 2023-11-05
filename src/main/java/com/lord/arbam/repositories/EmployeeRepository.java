package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
