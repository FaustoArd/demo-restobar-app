package com.lord.arbam.services_impl;

import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.repositories.IngredientCategoryRepository;
import com.lord.arbam.services.IngredientCategoryService;

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {
	
	private final IngredientCategoryRepository ingredientCategoryRepository;
	
	public IngredientCategoryServiceImpl(IngredientCategoryRepository ingredientCategoryRepository) {
		this.ingredientCategoryRepository = ingredientCategoryRepository;
	}

	@Override
	public IngredientCategory saveCategory(IngredientCategory ingredientCategory) {
		return ingredientCategoryRepository.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findById(Long id) {
		return ingredientCategoryRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("No se encontro la categoria del ingrediente"));
	}

}
