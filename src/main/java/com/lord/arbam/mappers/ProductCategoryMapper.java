package com.lord.arbam.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.ProductCategoryDto;
import com.lord.arbam.models.ProductCategory;

@Mapper
public interface ProductCategoryMapper {
	
	public static ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);
	
	public List<ProductCategoryDto> toCategoriesDto(List<ProductCategory> categories);

}
