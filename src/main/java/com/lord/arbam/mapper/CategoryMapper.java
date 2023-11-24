package com.lord.arbam.mapper;

import java.util.List;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.IngredientCategoryDto;
import com.lord.arbam.dto.ProductCategoryDto;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.ProductCategory;

@Mapper
public interface CategoryMapper {
	
	/**Product Category**/
	public static CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	public ProductCategory toProductCategory(ProductCategoryDto productCategoryDto);
	
	public ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);
	
	public List<ProductCategoryDto> toProductCategoriesDto(List<ProductCategory> categories);
	
	
	/**Ingredient Category**/
	public IngredientCategory toIngredientCategory(IngredientCategoryDto ingredientCategoryDto);
	
	public IngredientCategoryDto toIngredientCategoryDto(IngredientCategory ingredientCategory);
	
	public List<IngredientCategoryDto> toIngredientCategoriesDto(List<IngredientCategory> categories);
	
	
	
	
	
	

}
