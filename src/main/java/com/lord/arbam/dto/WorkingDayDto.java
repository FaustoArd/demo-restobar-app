package com.lord.arbam.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lord.arbam.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingDayDto {

	private Long id;
	
	
	@JsonFormat(pattern="dd-MM-yyyy")
	private Calendar date;

	private BigDecimal totalStartCash;

	private BigDecimal totalWorkingDay;

	private BigDecimal totalCash;
	
	private BigDecimal totalWorkingDayWithDiscount;

	private BigDecimal totalDebit;

	private BigDecimal totalTransf;
	
	private BigDecimal totalCredit;
	
	private BigDecimal totalMP;
	
	private BigDecimal totalEmployeeSalary;

	private List<Employee> employees;

	private boolean dayStarted;

}
