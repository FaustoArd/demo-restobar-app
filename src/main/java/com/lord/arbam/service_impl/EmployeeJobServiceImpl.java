package com.lord.arbam.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.repository.EmployeeJobRepository;
import com.lord.arbam.service.EmployeeJobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeJobServiceImpl implements EmployeeJobService{
	
	@Autowired
	private final EmployeeJobRepository employeeJobRepository;

	@Override
	public EmployeeJob findJobById(Long id) {
		return employeeJobRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Job not found"));
	}

	@Override
	public EmployeeJob saveJob(EmployeeJob employeeJob) {
		return employeeJobRepository.save(employeeJob);
	}
	
	@Override
	public EmployeeJob updateEmployeeJob(EmployeeJob employeeJob) {
		return null;
	}
	

	@Override
	public List<EmployeeJob> findAllJobs() {
	return (List<EmployeeJob>)employeeJobRepository.findAll();
	}

	@Override
	public void deleteJobById(Long id) {
		if(employeeJobRepository.existsById(id)) {
			employeeJobRepository.deleteById(id);
		}else {
			throw new ItemNotFoundException("Job not found");
		}
		
		
	}

	@Override
	public List<EmployeeJob> findAllbyJobRoleAsc() {
		return (List<EmployeeJob>)employeeJobRepository.findAllByOrderByJobRoleAsc();
	}

	

}
