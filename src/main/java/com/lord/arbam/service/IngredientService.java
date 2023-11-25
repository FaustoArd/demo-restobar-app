package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;

public interface IngredientService {
	
	public Ingredient findIngredientById(Long id);
	
	public Ingredient saveIngredient(IngredientCategory category,Ingredient ingredient);
	
	public Ingredient updateIngredient(IngredientCategory category,Ingredient ingredient);
	
	public void updateIngredientAmount(Integer stockRequired, Long productId);
	
	public void deleteIngredientById(Long id);
	
	public List<Ingredient> findAllIngredients();
	
	public void increaseIngredientAmount(Integer stockDeleted,Long productId);
	
	public List<Ingredient> findAllIngredientsOrderByNameAsc();

}
