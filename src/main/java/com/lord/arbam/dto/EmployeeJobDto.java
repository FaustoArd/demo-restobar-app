package com.lord.arbam.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeJobDto {

	private Long id;
	
	private String jobRole;
	
	private BigDecimal employeeSalary;
	
	
}
