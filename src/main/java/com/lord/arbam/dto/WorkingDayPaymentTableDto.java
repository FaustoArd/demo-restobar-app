package com.lord.arbam.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingDayPaymentTableDto {

	private BigDecimal totalStartCash;

	private BigDecimal totalWorkingDay;

	private BigDecimal totalCash;
	
	private BigDecimal totalWorkingDayWithDiscount;

	private BigDecimal totalDebit;

	private BigDecimal totalTransf;
	
	private BigDecimal totalCredit;
	
	private BigDecimal totalMP;
	
	private BigDecimal totalQR;
	
	private BigDecimal totalDigitalAmount;
	
	private BigDecimal totalEmployeeSalary;
}
