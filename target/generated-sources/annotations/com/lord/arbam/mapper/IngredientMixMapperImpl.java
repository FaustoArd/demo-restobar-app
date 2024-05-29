package com.lord.arbam.mapper;

import com.lord.arbam.dto.IngredientMixDto;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T19:15:17-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class IngredientMixMapperImpl implements IngredientMixMapper {

    @Override
    public IngredientMix toIngredientMix(IngredientMixDto ingredientMixDto) {
        if ( ingredientMixDto == null ) {
            return null;
        }

        IngredientMix.IngredientMixBuilder ingredientMix = IngredientMix.builder();

        ingredientMix.product( ingredientMixDtoToProduct( ingredientMixDto ) );
        ingredientMix.ingredient( ingredientMixDtoToIngredient( ingredientMixDto ) );
        ingredientMix.id( ingredientMixDto.getId() );
        ingredientMix.ingredientAmount( ingredientMixDto.getIngredientAmount() );

        return ingredientMix.build();
    }

    @Override
    public IngredientMixDto toIngredientMixDto(IngredientMix ingredientMix) {
        if ( ingredientMix == null ) {
            return null;
        }

        IngredientMixDto ingredientMixDto = new IngredientMixDto();

        ingredientMixDto.setProductId( ingredientMixProductId( ingredientMix ) );
        ingredientMixDto.setProductName( ingredientMixProductProductName( ingredientMix ) );
        ingredientMixDto.setIngredientId( ingredientMixIngredientId( ingredientMix ) );
        ingredientMixDto.setIngredientName( ingredientMixIngredientIngredientName( ingredientMix ) );
        ingredientMixDto.setId( ingredientMix.getId() );
        ingredientMixDto.setIngredientAmount( ingredientMix.getIngredientAmount() );

        return ingredientMixDto;
    }

    @Override
    public List<IngredientMixDto> toIngredientsMixesDto(List<IngredientMix> ingredientMixes) {
        if ( ingredientMixes == null ) {
            return null;
        }

        List<IngredientMixDto> list = new ArrayList<IngredientMixDto>( ingredientMixes.size() );
        for ( IngredientMix ingredientMix : ingredientMixes ) {
            list.add( toIngredientMixDto( ingredientMix ) );
        }

        return list;
    }

    protected Product ingredientMixDtoToProduct(IngredientMixDto ingredientMixDto) {
        if ( ingredientMixDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( ingredientMixDto.getProductId() );

        return product.build();
    }

    protected Ingredient ingredientMixDtoToIngredient(IngredientMixDto ingredientMixDto) {
        if ( ingredientMixDto == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.id( ingredientMixDto.getIngredientId() );
        ingredient.ingredientName( ingredientMixDto.getIngredientName() );

        return ingredient.build();
    }

    private Long ingredientMixProductId(IngredientMix ingredientMix) {
        if ( ingredientMix == null ) {
            return null;
        }
        Product product = ingredientMix.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String ingredientMixProductProductName(IngredientMix ingredientMix) {
        if ( ingredientMix == null ) {
            return null;
        }
        Product product = ingredientMix.getProduct();
        if ( product == null ) {
            return null;
        }
        String productName = product.getProductName();
        if ( productName == null ) {
            return null;
        }
        return productName;
    }

    private Long ingredientMixIngredientId(IngredientMix ingredientMix) {
        if ( ingredientMix == null ) {
            return null;
        }
        Ingredient ingredient = ingredientMix.getIngredient();
        if ( ingredient == null ) {
            return null;
        }
        Long id = ingredient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String ingredientMixIngredientIngredientName(IngredientMix ingredientMix) {
        if ( ingredientMix == null ) {
            return null;
        }
        Ingredient ingredient = ingredientMix.getIngredient();
        if ( ingredient == null ) {
            return null;
        }
        String ingredientName = ingredient.getIngredientName();
        if ( ingredientName == null ) {
            return null;
        }
        return ingredientName;
    }
}
