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
import com.lord.arbam.dto.ProductCategoryDto;
import com.lord.arbam.mapper.CategoryMapper;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	@Autowired
	private final CategoryService<ProductCategory> productCategoryService;
	
	@Autowired
	private final CategoryService<IngredientCategory> ingredientCategoryService;
	
	
	
	/**Product Category**/
	@GetMapping("/all")
	ResponseEntity<List<ProductCategoryDto>> findAllCategories(){
		List<ProductCategory> categories = productCategoryService.findAllCategoriesOrderByNamAsc();
		List<ProductCategoryDto> categoriesDto = CategoryMapper.INSTANCE.toProductCategoriesDto(categories);
		return new ResponseEntity<List<ProductCategoryDto>>(categoriesDto,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<ProductCategoryDto> findProductCategoryById(@PathVariable("id")Long id){
		ProductCategory category = productCategoryService.findCategoryById(id);
		ProductCategoryDto categoryDto = CategoryMapper.INSTANCE.toProductCategoryDto(category);
		return new ResponseEntity<ProductCategoryDto>(categoryDto,HttpStatus.OK);
	}
	
	@PostMapping("/")
	ResponseEntity<ProductCategoryDto> saveProductCategory(@RequestBody ProductCategoryDto categoryDto){
		ProductCategory category = CategoryMapper.INSTANCE.toProductCategory(categoryDto);
		ProductCategory savedCategory = productCategoryService.saveCategory(category);
		ProductCategoryDto savedCategoryDto = CategoryMapper.INSTANCE.toProductCategoryDto(savedCategory);
		return new ResponseEntity<ProductCategoryDto>(savedCategoryDto,HttpStatus.CREATED);
	}
	@PutMapping("/update")
	ResponseEntity<ProductCategoryDto> updateProductCategory(@RequestBody ProductCategoryDto categoryDto){
		ProductCategory category = CategoryMapper.INSTANCE.toProductCategory(categoryDto);
		ProductCategory updatedCategory = productCategoryService.saveCategory(category);
		ProductCategoryDto updatedCategoryDto = CategoryMapper.INSTANCE.toProductCategoryDto(updatedCategory);
		return new ResponseEntity<ProductCategoryDto>(updatedCategoryDto,HttpStatus.CREATED);
	}
	
	/**Ingredient Category**/
	@GetMapping("/all_ingredient")
	ResponseEntity<List<IngredientCategoryDto>> findAllIngredientCategories(){
		List<IngredientCategory> categories = ingredientCategoryService.findAllCategories();
		List<IngredientCategoryDto> categoriesDto = CategoryMapper.INSTANCE.toIngredientCategoriesDto(categories);
		return new ResponseEntity<List<IngredientCategoryDto>>(categoriesDto,HttpStatus.OK);
	}
	
	@GetMapping("/ingredient/{id}")
	ResponseEntity<IngredientCategoryDto> findIngredientCategoryById(@PathVariable("id")Long id){
		IngredientCategory cat = ingredientCategoryService.findCategoryById(id);
		IngredientCategoryDto catDto = CategoryMapper.INSTANCE.toIngredientCategoryDto(cat);
		return new ResponseEntity<IngredientCategoryDto>(catDto,HttpStatus.OK);
	}
	
	@PostMapping("/save_ingredient")
	ResponseEntity<IngredientCategoryDto> saveIngredientCategory( @RequestBody IngredientCategoryDto ingredientCategoryDto){
		IngredientCategory category = CategoryMapper.INSTANCE.toIngredientCategory(ingredientCategoryDto);
		IngredientCategory newCategory = ingredientCategoryService.saveCategory(category);
		IngredientCategoryDto newCategoryDto =CategoryMapper.INSTANCE.toIngredientCategoryDto(newCategory);
		return new ResponseEntity<IngredientCategoryDto>(newCategoryDto,HttpStatus.CREATED);
	}
	

}
