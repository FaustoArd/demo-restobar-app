package com.lord.arbam.dtos;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {
	
	private Long id;
	
	private String ingredientName;
	
	private Long categoryId;
	
	private String categoryName;
	
	private Integer ingredientAmount;
	
	private Calendar expirationDate;
}
