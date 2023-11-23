package com.lord.arbam.service;



import java.util.List;

import com.lord.arbam.model.IngredientMix;

public interface IngredientMixService {
	
	public IngredientMix findIngredientMixById(Long id);
	
	public IngredientMix saveIngredientMix(IngredientMix ingredientMix);
	
	public IngredientMix updateIngredientMix(IngredientMix ingredientMix);
	
	public List<IngredientMix> findByProductId(Long id);
	
	public void deleteIngredientMixById(Long id);
	
	public List<IngredientMix> findAllIngredientMixes();

}
