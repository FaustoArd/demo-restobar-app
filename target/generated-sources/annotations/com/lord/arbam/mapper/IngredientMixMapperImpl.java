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
    date = "2023-11-23T22:16:39-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class IngredientMixMapperImpl implements IngredientMixMapper {

    @Override
    public IngredientMix toProductMix(IngredientMixDto productMixDto) {
        if ( productMixDto == null ) {
            return null;
        }

        IngredientMix.IngredientMixBuilder ingredientMix = IngredientMix.builder();

        ingredientMix.product( ingredientMixDtoToProduct( productMixDto ) );
        ingredientMix.ingredient( ingredientMixDtoToIngredient( productMixDto ) );
        ingredientMix.id( productMixDto.getId() );
        ingredientMix.ingredientAmount( productMixDto.getIngredientAmount() );

        return ingredientMix.build();
    }

    @Override
    public IngredientMixDto toPpoductMixDto(IngredientMix productMix) {
        if ( productMix == null ) {
            return null;
        }

        IngredientMixDto ingredientMixDto = new IngredientMixDto();

        ingredientMixDto.setProductId( productMixProductId( productMix ) );
        ingredientMixDto.setProductName( productMixProductProductName( productMix ) );
        ingredientMixDto.setIngredientId( productMixIngredientId( productMix ) );
        ingredientMixDto.setIngredientName( productMixIngredientIngredientName( productMix ) );
        ingredientMixDto.setId( productMix.getId() );
        ingredientMixDto.setIngredientAmount( productMix.getIngredientAmount() );

        return ingredientMixDto;
    }

    @Override
    public List<IngredientMixDto> toProductsMixDto(List<IngredientMix> productsMixes) {
        if ( productsMixes == null ) {
            return null;
        }

        List<IngredientMixDto> list = new ArrayList<IngredientMixDto>( productsMixes.size() );
        for ( IngredientMix ingredientMix : productsMixes ) {
            list.add( toPpoductMixDto( ingredientMix ) );
        }

        return list;
    }

    protected Product ingredientMixDtoToProduct(IngredientMixDto ingredientMixDto) {
        if ( ingredientMixDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( ingredientMixDto.getProductId() );
        product.productName( ingredientMixDto.getProductName() );

        return product.build();
    }

    protected Ingredient ingredientMixDtoToIngredient(IngredientMixDto ingredientMixDto) {
        if ( ingredientMixDto == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.id( ingredientMixDto.getIngredientId() );

        return ingredient.build();
    }

    private Long productMixProductId(IngredientMix ingredientMix) {
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

    private String productMixProductProductName(IngredientMix ingredientMix) {
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

    private Long productMixIngredientId(IngredientMix ingredientMix) {
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

    private String productMixIngredientIngredientName(IngredientMix ingredientMix) {
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
