package com.lord.arbam.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lord.arbam.dtos.IngredientDto;
import com.lord.arbam.mappers.IngredientMapper;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.services.CategoryService;
import com.lord.arbam.services.IngredientService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/ingredients")
@RequiredArgsConstructor
public class IngredientController {
	
	@Autowired
	private final IngredientService ingredientService;
	
	@Autowired
	private final CategoryService<IngredientCategory> categoryService;

	
	@GetMapping("/all")
	ResponseEntity<List<IngredientDto>> findAllIngredients(){
		List<Ingredient> ingredients = ingredientService.findAllIngredients();
		List<IngredientDto> ingredientsDto = IngredientMapper.INSTANCE.toIngredientsDto(ingredients);
		return new ResponseEntity<List<IngredientDto>>(ingredientsDto,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<IngredientDto> findIngredientById(@PathVariable("id")Long id){
		Ingredient ingredient = ingredientService.findIngredientById(id);
		IngredientDto ingredientDto = IngredientMapper.INSTANCE.toIngredientDto(ingredient);
		return new ResponseEntity<IngredientDto>(ingredientDto,HttpStatus.OK);
	}
	
	@PostMapping("/")
	ResponseEntity<IngredientDto> saveIngredient(@RequestBody IngredientDto ingredientDto){
		Ingredient ingredient = IngredientMapper.INSTANCE.toIngredient(ingredientDto);
		IngredientCategory category = categoryService.findCategoryById(ingredient.getIngredientCategory().getId());
		Ingredient savedIngredient = ingredientService.saveIngredient(category,ingredient);
		IngredientDto savedIngredientDto = IngredientMapper.INSTANCE.toIngredientDto(savedIngredient);
		return new ResponseEntity<IngredientDto>(savedIngredientDto,HttpStatus.CREATED);
	}
	@PutMapping("/")
	ResponseEntity<IngredientDto> updateIngredient(@RequestBody IngredientDto ingredientDto){
		Ingredient ingredient = IngredientMapper.INSTANCE.toIngredient(ingredientDto);
		IngredientCategory category = categoryService.findCategoryById(ingredient.getIngredientCategory().getId());
		Ingredient savedIngredient = ingredientService.updateIngredient(category,ingredient);
		IngredientDto savedIngredientDto = IngredientMapper.INSTANCE.toIngredientDto(savedIngredient);
		return new ResponseEntity<IngredientDto>(savedIngredientDto,HttpStatus.OK);
	}
}
