package com.lord.arbam.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IngredientStockDto {

	private long id;
	
	private String ingredientName;
	
	private int ingredientAmount;
	
	public IngredientStockDto(long id,String ingredientName,int ingredientAmount) {
		super();
		this.id = id;
		this.ingredientName = ingredientName;
		this.ingredientAmount = ingredientAmount;
	}
}
