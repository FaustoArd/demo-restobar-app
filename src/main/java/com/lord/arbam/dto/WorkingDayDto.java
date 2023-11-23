package com.lord.arbam.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.lord.arbam.model.Employee;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingDayDto {

	private Long id;

	private BigDecimal totalStartCash;

	private BigDecimal totalWorkingDay;

	private BigDecimal totalCash;
	
	private BigDecimal totalCashWithDiscount;

	private BigDecimal totalDebit;

	private BigDecimal totalTransf;
	
	private BigDecimal totalCredit;
	
	private BigDecimal totalMP;
	
	private BigDecimal totalEmployeeSalary;

	private List<Employee> employees;

	private boolean dayStarted;

}
