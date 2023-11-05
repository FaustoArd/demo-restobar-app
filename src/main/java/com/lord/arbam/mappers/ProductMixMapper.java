package com.lord.arbam.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.ProductMixDto;
import com.lord.arbam.models.ProductMix;

@Mapper
public interface ProductMixMapper {

	public static ProductMixMapper INSTANCE = Mappers.getMapper(ProductMixMapper.class);
	
	@Mapping(target="product.id", source="productId")
	@Mapping(target="product.productName", source="productName")
	@Mapping(target="ingredient.id", source="ingredientId")
	public ProductMix toProductMix(ProductMixDto productMixDto);
	
	
	@Mapping(target="productId", source="product.id")
	@Mapping(target="productName", source="product.productName")
	@Mapping(target="ingredientId", source="ingredient.id")
	@Mapping(target="ingredientName", source="ingredient.ingredientName")
	public ProductMixDto toPpoductMixDto(ProductMix productMix);
	
	@Mapping(target="productId", source="product.id")
	@Mapping(target="productName", source="product.productName")
	@Mapping(target="ingredientId", source="ingredient.id")
	@Mapping(target="ingredientName", source="ingredient.ingredientName")
	public List<ProductMixDto> toProductsMixDto(List<ProductMix> productsMixes);
}
