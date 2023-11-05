package com.lord.arbam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.IngredientDto;
import com.lord.arbam.mappers.IngredientMapper;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.services.IngredientCategoryService;
import com.lord.arbam.services.IngredientService;
import com.lord.arbam.services.ProductCategoryService;

@RestController
@RequestMapping("/api/v1/arbam/ingredients")
public class IngredientController {
	
	@Autowired
	private final IngredientService ingredientService;
	
	@Autowired
	private final IngredientCategoryService categoryService;

	public IngredientController(IngredientService ingredientService,IngredientCategoryService categoryService) {
		this.ingredientService = ingredientService;
		this.categoryService = categoryService;
	}
	
	@PostMapping("/")
	ResponseEntity<IngredientDto> saveIngredient(@RequestBody IngredientDto ingredientDto){
		Ingredient ingredient = IngredientMapper.INSTANCE.toIngredient(ingredientDto);
		IngredientCategory category = categoryService.findById(ingredient.getIngredientCategory().getId());
		Ingredient savedIngredient = ingredientService.saveIngredient(category,ingredient);
		IngredientDto savedIngredientDto = IngredientMapper.INSTANCE.toIngredientDto(savedIngredient);
		return new ResponseEntity<IngredientDto>(savedIngredientDto,HttpStatus.CREATED);
	}
	@PutMapping("/")
	ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientDto ingredientDto){
		Ingredient ingredient = IngredientMapper.INSTANCE.toIngredient(ingredientDto);
		IngredientCategory category = categoryService.findById(ingredient.getIngredientCategory().getId());
		Ingredient savedIngredient = ingredientService.updateIngredient(category,ingredient);
		IngredientDto savedIngredientDto = IngredientMapper.INSTANCE.toIngredientDto(savedIngredient);
		return new ResponseEntity<IngredientDto>(savedIngredientDto,HttpStatus.OK);
	}
}
