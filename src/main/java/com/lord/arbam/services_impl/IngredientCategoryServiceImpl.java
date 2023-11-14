package com.lord.arbam.services_impl;

import org.springframework.stereotype.Service;

import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.repositories.IngredientCategoryRepository;
import com.lord.arbam.services.IngredientCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientCategoryServiceImpl implements IngredientCategoryService {
	
	private final IngredientCategoryRepository ingredientCategoryRepository;
	


	@Override
	public IngredientCategory saveCategory(IngredientCategory ingredientCategory) {
		return ingredientCategoryRepository.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findCategoryById(Long id) {
		return ingredientCategoryRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("No se encontro la categoria del ingrediente"));
	}

}
