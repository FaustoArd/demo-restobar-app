package com.lord.arbam.dtos;

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
	
	private Double totalTablePrice;
	
	private Long tableOrderId;
	
	private Calendar closeTime;
	
	private boolean open;

}
