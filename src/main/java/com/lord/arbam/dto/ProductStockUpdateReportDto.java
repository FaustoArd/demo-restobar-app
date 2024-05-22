package com.lord.arbam.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductStockUpdateReportDto {

	private String productName;
	
	private int productOldQuantity;
	
	private int productNewQuantity;
	
	private List<IngredientStockUpdateReportDto> ingrdientStockReports;
}
