package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.EmployeeJob;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, Long> {

}
