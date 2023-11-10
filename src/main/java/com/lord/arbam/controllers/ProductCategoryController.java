package com.lord.arbam.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.ProductCategoryDto;
import com.lord.arbam.mappers.ProductCategoryMapper;
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.services.ProductCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/categories")
@RequiredArgsConstructor
public class ProductCategoryController {
	
	@Autowired
	private final ProductCategoryService productCategoryService;
	
	@GetMapping("/all")
	ResponseEntity<List<ProductCategoryDto>> findAllCategories(){
		List<ProductCategory> categories = productCategoryService.findAllCategories();
		List<ProductCategoryDto> categoriesDto = ProductCategoryMapper.INSTANCE.toCategoriesDto(categories);
		return new ResponseEntity<List<ProductCategoryDto>>(categoriesDto,HttpStatus.OK);
	}

}
