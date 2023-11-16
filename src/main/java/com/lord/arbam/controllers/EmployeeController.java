package com.lord.arbam.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.EmployeeDto;
import com.lord.arbam.mappers.EmployeeMapper;
import com.lord.arbam.models.Employee;
import com.lord.arbam.services.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/employees")
@RequiredArgsConstructor
public class EmployeeController {
	
	@Autowired
	private final EmployeeService employeeService;
	
	
	@GetMapping("/all")
	ResponseEntity<List<EmployeeDto>> findAllemployees(){
		List<Employee> employees = employeeService.findAllEmployees();
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto,HttpStatus.OK);
		
		
	}
	@GetMapping("/all_by_id")
	ResponseEntity<List<EmployeeDto>> findAllById(@RequestParam List<Long> waitressesIds){
		List<Employee> waitresses = employeeService.findAllById(waitressesIds);
		List<EmployeeDto> waitressesDto = EmployeeMapper.INSTANCE.toEmployeesDto(waitresses);
		return new ResponseEntity<List<EmployeeDto>>(waitressesDto,HttpStatus.OK);
	}
	
	@GetMapping("/by_role")
	ResponseEntity<List<EmployeeDto>> findbyJobRole(@RequestParam String jobRole){
		List<Employee> employees = employeeService.findByEmployeeJobJobRole(jobRole);
		List<EmployeeDto> employeesDto = EmployeeMapper.INSTANCE.toEmployeesDto(employees);
		return new ResponseEntity<List<EmployeeDto>>(employeesDto,HttpStatus.OK);
	}
	
	

}
