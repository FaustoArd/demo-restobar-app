package com.lord.arbam.services;

import com.lord.arbam.models.IngredientCategory;

public interface IngredientCategoryService {
	
	public IngredientCategory saveCategory(IngredientCategory ingredientCategory);
	
	public IngredientCategory findCategoryById(Long id);

}
