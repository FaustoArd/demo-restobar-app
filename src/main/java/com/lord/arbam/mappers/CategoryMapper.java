package com.lord.arbam.mappers;

import java.util.List;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.ProductCategoryDto;
import com.lord.arbam.models.ProductCategory;

@Mapper
public interface CategoryMapper {
	
	public static CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	public ProductCategory toProductCategory(ProductCategoryDto productCategoryDto);
	
	public ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);
	
	public List<ProductCategoryDto> toCategoriesDto(List<ProductCategory> categories);
	
	

}
