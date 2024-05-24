package com.lord.arbam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientStockUpdateReportDto {
	
	private String ingredientName;
	
	private boolean substracted;
	
	private boolean successful;
	
	private int ingredientRecipeQuantity;
	
	private int ingredientQuantitySubstracted;
	
	private int ingredientQuantityIncreased;
	
	private int ingredientOldQuantity;
	
	private int ingredientNewQuantity;
	
	private int ingredientQuantityRequired;

}
