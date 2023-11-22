package com.lord.arbam.services;

import java.util.List;

import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;

public interface IngredientService {
	
	public Ingredient findIngredientById(Long id);
	
	public Ingredient saveIngredient(IngredientCategory category,Ingredient ingredient);
	
	public Ingredient updateIngredient(IngredientCategory category,Ingredient ingredient);
	
	public void updateIngredientAmount(Integer amountSubtracted, Long productId);
	
	public void deleteIngredientById(Long id);
	
	public List<Ingredient> findAllIngredients();

}
