package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.dto.IngredientItemStockReportDto;
import com.lord.arbam.dto.IngredientStockDto;
import com.lord.arbam.dto.IngredientStockReportDto;
import com.lord.arbam.dto.IngredientStockUpdateReportDto;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductStock;

public interface IngredientService {
	
	public Ingredient findIngredientById(Long id);
	
	public Ingredient saveIngredient(IngredientCategory category,Ingredient ingredient);
	
	public Ingredient updateIngredient(IngredientCategory category,Ingredient ingredient);
	
	public List<IngredientStockUpdateReportDto> decreaseIngredientAmount(Integer stockRequired, Long productId);
	
	public void deleteIngredientById(Long id);
	
	public List<Ingredient> findAllIngredients();
	
	public List<IngredientStockUpdateReportDto> increaseIngredientAmount(Integer stockDeleted,Long productId);
	
	public List<Ingredient> findAllIngredientsOrderByNameAsc();
	
	public IngredientStockDto increaseIngredientStock(IngredientStockDto ingredientStockDto);
	
	public IngredientStockDto DecreaseIngredientStock(IngredientStockDto ingredientStockDto);
	
	public List<IngredientItemStockReportDto> checkIngredientsStock(long productId, int stock);

}
