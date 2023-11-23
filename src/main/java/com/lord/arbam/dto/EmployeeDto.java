package com.lord.arbam.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeDto {
	
	private Long id;
	
	private String employeeName;
	
	private Long employeeJobId;
	
	private String employeeJob;
	
	private String employeeSalary;

}
