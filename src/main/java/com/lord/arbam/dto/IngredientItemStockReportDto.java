package com.lord.arbam.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IngredientItemStockReportDto {

	private String ingredientName;

	private int ingredientCurrentQuantity;

	private int ingredientQuantityRequired;
	private int ingredientMixQuantity;

}
