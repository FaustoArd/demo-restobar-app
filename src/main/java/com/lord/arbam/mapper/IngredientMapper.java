package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.IngredientDto;
import com.lord.arbam.model.Ingredient;

@Mapper
public interface IngredientMapper {

	public static IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);
	
	@Mapping(target="ingredientCategory.id", source="categoryId")
	@Mapping(target="ingredientCategory.categoryName", source="categoryName")
	public Ingredient toIngredient(IngredientDto recipentDto);
	
	@Mapping(target="categoryId", source="ingredientCategory.id")
	@Mapping(target="categoryName", source="ingredientCategory.categoryName")
	public IngredientDto toIngredientDto(Ingredient recipent);
	
	@Mapping(target="categoryId", source="ingredientCategory.id")
	@Mapping(target="categoryName", source="ingredientCategory.categoryName")
	public List<IngredientDto> toIngredientsDto(List<Ingredient> ingredients);
	
}
