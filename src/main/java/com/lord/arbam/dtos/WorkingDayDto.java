package com.lord.arbam.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingDayDto {
	
	private Long id;
	

	private Double totalStartCash;
	
	
	private BigDecimal totalWorkingDay;
	
	
	private BigDecimal totalPostEmployeeSalary;
	
	
	private BigDecimal totalCash;
	

	private BigDecimal totalDebit;
	

	private BigDecimal totalTransf;
	
	
	private BigDecimal totalEmployeeSalary;
}
