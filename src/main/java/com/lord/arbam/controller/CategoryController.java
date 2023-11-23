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

import com.lord.arbam.dto.ProductCategoryDto;
import com.lord.arbam.mapper.CategoryMapper;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	@Autowired
	private final CategoryService<ProductCategory> productCategoryService;
	
	@GetMapping("/all")
	ResponseEntity<List<ProductCategoryDto>> findAllCategories(){
		List<ProductCategory> categories = productCategoryService.findAllCategories();
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
	ResponseEntity<ProductCategoryDto> updateProduct(@RequestBody ProductCategoryDto categoryDto){
		ProductCategory category = CategoryMapper.INSTANCE.toProductCategory(categoryDto);
		ProductCategory updatedCategory = productCategoryService.saveCategory(category);
		ProductCategoryDto updatedCategoryDto = CategoryMapper.INSTANCE.toProductCategoryDto(updatedCategory);
		return new ResponseEntity<ProductCategoryDto>(updatedCategoryDto,HttpStatus.CREATED);
	}
	

}
