package com.lord.arbam.services_impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.repositories.IngredientCategoryRepository;
import com.lord.arbam.services.CategoryService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientCategoryServiceImpl implements CategoryService<IngredientCategory> {
	
	@Autowired
	private final IngredientCategoryRepository ingredientCategoryRepository;

	@Override
	public IngredientCategory findCategoryById(Long id) {
		return ingredientCategoryRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("No se encontro la categoria del ingrediente"));
	}

	@Override
	public IngredientCategory saveCategory(IngredientCategory category) {
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory updateCategory(IngredientCategory category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategoryById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IngredientCategory> findAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}
	


	

}
