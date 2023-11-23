package com.lord.arbam.controller;

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

import com.lord.arbam.dto.IngredientCategoryDto;
import com.lord.arbam.dto.IngredientDto;
import com.lord.arbam.mapper.CategoryMapper;
import com.lord.arbam.mapper.IngredientMapper;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.IngredientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/ingredients")
@RequiredArgsConstructor
public class IngredientController {
	
	@Autowired
	private final IngredientService ingredientService;
	
	@Autowired
	private final CategoryService<IngredientCategory> categoryService;

	
	
	@GetMapping("/all_categories")
	ResponseEntity<List<IngredientCategoryDto>> findAllCategories(){
		List<IngredientCategory> categories = categoryService.findAllCategories();
		List<IngredientCategoryDto> categoriesDto = CategoryMapper.INSTANCE.toIngredientCategoriesDto(categories);
		return new ResponseEntity<List<IngredientCategoryDto>>(categoriesDto,HttpStatus.OK);
	}
	
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
