package com.lord.arbam.mapper;

import com.lord.arbam.dto.IngredientDto;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-25T16:49:11-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public Ingredient toIngredient(IngredientDto recipentDto) {
        if ( recipentDto == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.ingredientCategory( ingredientDtoToIngredientCategory( recipentDto ) );
        ingredient.expirationDate( recipentDto.getExpirationDate() );
        ingredient.id( recipentDto.getId() );
        ingredient.ingredientAmount( recipentDto.getIngredientAmount() );
        ingredient.ingredientName( recipentDto.getIngredientName() );

        return ingredient.build();
    }

    @Override
    public IngredientDto toIngredientDto(Ingredient recipent) {
        if ( recipent == null ) {
            return null;
        }

        IngredientDto ingredientDto = new IngredientDto();

        ingredientDto.setCategoryId( recipentIngredientCategoryId( recipent ) );
        ingredientDto.setCategoryName( recipentIngredientCategoryCategoryName( recipent ) );
        ingredientDto.setExpirationDate( recipent.getExpirationDate() );
        ingredientDto.setId( recipent.getId() );
        ingredientDto.setIngredientAmount( recipent.getIngredientAmount() );
        ingredientDto.setIngredientName( recipent.getIngredientName() );

        return ingredientDto;
    }

    @Override
    public List<IngredientDto> toIngredientsDto(List<Ingredient> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientDto> list = new ArrayList<IngredientDto>( ingredients.size() );
        for ( Ingredient ingredient : ingredients ) {
            list.add( toIngredientDto( ingredient ) );
        }

        return list;
    }

    protected IngredientCategory ingredientDtoToIngredientCategory(IngredientDto ingredientDto) {
        if ( ingredientDto == null ) {
            return null;
        }

        IngredientCategory.IngredientCategoryBuilder ingredientCategory = IngredientCategory.builder();

        ingredientCategory.id( ingredientDto.getCategoryId() );
        ingredientCategory.categoryName( ingredientDto.getCategoryName() );

        return ingredientCategory.build();
    }

    private Long recipentIngredientCategoryId(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }
        IngredientCategory ingredientCategory = ingredient.getIngredientCategory();
        if ( ingredientCategory == null ) {
            return null;
        }
        Long id = ingredientCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String recipentIngredientCategoryCategoryName(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }
        IngredientCategory ingredientCategory = ingredient.getIngredientCategory();
        if ( ingredientCategory == null ) {
            return null;
        }
        String categoryName = ingredientCategory.getCategoryName();
        if ( categoryName == null ) {
            return null;
        }
        return categoryName;
    }
}
