package com.lord.arbam.dtos;

import java.math.BigDecimal;
import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableDto {
	
	private Long id;
	
	private Integer tableNumber;
	
	private Long employeeId;
	
	private String employeeName;
	
	private BigDecimal totalTablePrice;
	
	private Long tableOrderId;
	
	private Calendar closeTime;
	
	private boolean open;

}
