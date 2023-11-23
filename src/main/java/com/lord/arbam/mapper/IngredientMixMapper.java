package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.IngredientMixDto;
import com.lord.arbam.model.IngredientMix;

@Mapper
public interface IngredientMixMapper {

	public static IngredientMixMapper INSTANCE = Mappers.getMapper(IngredientMixMapper.class);
	
	@Mapping(target="product.id", source="productId")
	@Mapping(target="product.productName", source="productName")
	@Mapping(target="ingredient.id", source="ingredientId")
	public IngredientMix toProductMix(IngredientMixDto productMixDto);
	
	
	@Mapping(target="productId", source="product.id")
	@Mapping(target="productName", source="product.productName")
	@Mapping(target="ingredientId", source="ingredient.id")
	@Mapping(target="ingredientName", source="ingredient.ingredientName")
	public IngredientMixDto toPpoductMixDto(IngredientMix productMix);
	
	@Mapping(target="productId", source="product.id")
	@Mapping(target="productName", source="product.productName")
	@Mapping(target="ingredientId", source="ingredient.id")
	@Mapping(target="ingredientName", source="ingredient.ingredientName")
	public List<IngredientMixDto> toProductsMixDto(List<IngredientMix> productsMixes);
}
