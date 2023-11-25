package com.lord.arbam.service;



import java.util.List;

import org.springframework.data.domain.Sort;

import com.lord.arbam.model.IngredientMix;

public interface IngredientMixService {
	
	public IngredientMix findIngredientMixById(Long id);
	
	public IngredientMix saveIngredientMix(IngredientMix ingredientMix,Long productId);
	
	public IngredientMix updateIngredientMix(IngredientMix ingredientMix);
	
	public List<IngredientMix> findByProductId(Long id);
	
	public List<IngredientMix> findByProductIdByOrderAsc(Long id);
	
	public void deleteIngredientMixById(Long id);
	
	public List<IngredientMix> findAllIngredientMixes();
	
	

}
