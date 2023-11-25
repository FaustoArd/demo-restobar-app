package com.lord.arbam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.EmployeeJob;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, Long> {
	
	public List<EmployeeJob> findAllByOrderByJobRoleAsc();

}
