package com.lord.arbam.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.lord.arbam.models.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingDayDto {
	
	private Long id;
	
	private BigDecimal totalStartCash;
	
	private BigDecimal totalWorkingDay;
	
	private BigDecimal totalPostEmployeeSalary;
	
	private BigDecimal totalCash;
	
	private BigDecimal totalDebit;
	
	private BigDecimal totalTransf;
	
	private String cashierName;
	
	private List<Employee> employeesId;
	
	private BigDecimal totalWaitressSalary;
	
	private BigDecimal totalCashierSalary;
	
	private boolean dayStarted;
	
	
}
