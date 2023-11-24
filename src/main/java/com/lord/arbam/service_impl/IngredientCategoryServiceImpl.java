package com.lord.arbam.service_impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.repository.IngredientCategoryRepository;
import com.lord.arbam.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientCategoryServiceImpl implements CategoryService<IngredientCategory> {
	
	@Autowired
	private final IngredientCategoryRepository ingredientCategoryRepository;

	@Override
	public IngredientCategory findCategoryById(Long id) {
		return ingredientCategoryRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Ingredient category not found"));
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
		if(ingredientCategoryRepository.existsById(id)) {
			ingredientCategoryRepository.deleteById(id);
		}else {
			throw new ItemNotFoundException("Ingredient category not found");
		}
		
	}

	@Override
	public List<IngredientCategory> findAllCategories() {
		return (List<IngredientCategory>)ingredientCategoryRepository.findAll();
	}
	


	

}
