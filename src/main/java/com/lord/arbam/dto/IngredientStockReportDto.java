package com.lord.arbam.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientStockReportDto {
	
	
	private String productName;
	private int stockRequestedQuantity;
	
	
	List<IngredientItemStockReportDto> ingredients;
	

}
