package com.lord.arbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dto.EmployeeDto;
import com.lord.arbam.dto.EmployeeJobDto;
import com.lord.arbam.mapper.EmployeeMapper;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.service.EmployeeJobService;
import com.lord.arbam.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/employees")
@RequiredArgsConstructor
public class EmployeeController {
	
	@Autowired
	private final EmployeeService employeeService;
	
	@Autowired
	private final EmployeeJobService employeeJobService;
 	
	
	@GetMapping("/all")
	ResponseEntity<List<EmployeeDto>> findAllemployees(){
		List<Employee> employees = employeeService.findAllEmployees();
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto,HttpStatus.OK);
		
		
	}
	@GetMapping("/all_by_id")
	ResponseEntity<List<EmployeeDto>> findAllById(@RequestParam List<Long> employeesId){
		List<Employee> employees = employeeService.findAllById(employeesId);
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto,HttpStatus.OK);
	}
	
	@GetMapping("/by_role")
	ResponseEntity<List<EmployeeDto>> findbyJobRole(@RequestParam String jobRole){
		List<Employee> employees = employeeService.findByEmployeeJobJobRole(jobRole);
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto,HttpStatus.OK);
	}
	
	@GetMapping("/all_jobs")
	ResponseEntity<List<EmployeeJobDto>> findAllJobsOrderAsc(){
		List<EmployeeJob> jobs = employeeJobService.findAllbyJobRoleAsc();
		List<EmployeeJobDto> jobsDto = EmployeeMapper.INSTANCE.toEmployeeJobsDto(jobs);
		return new ResponseEntity<List<EmployeeJobDto>>(jobsDto,HttpStatus.OK);
	}
	
	@PostMapping("/new_job_role")
	ResponseEntity<EmployeeJobDto> createJobRole(@RequestBody EmployeeJobDto employeeJobDto){
		EmployeeJob job = EmployeeMapper.INSTANCE.toEmployeeJob(employeeJobDto);
		EmployeeJob savedJob = employeeJobService.saveJob(job);
		EmployeeJobDto savedJobDto = EmployeeMapper.INSTANCE.toEmployeeJobDto(savedJob);
		return new ResponseEntity<EmployeeJobDto>(savedJobDto,HttpStatus.CREATED);
	}
	
	

}
