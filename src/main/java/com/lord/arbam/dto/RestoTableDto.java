package com.lord.arbam.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoTableDto {
	
	private Long id;
	
	private Integer tableNumber;
	
	private String tableDescription;
	
	private Long employeeId;
	
	private String employeeName;
	
	private BigDecimal totalTablePrice;
	
	private boolean open;
	
	private boolean copy;
	
	List<RestoTableOrderDto> restoTableOrders;

}
