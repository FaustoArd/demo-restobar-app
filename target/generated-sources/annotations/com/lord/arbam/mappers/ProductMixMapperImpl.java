package com.lord.arbam.mappers;

import com.lord.arbam.dtos.ProductMixDto;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductMix;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-11T17:20:35-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class ProductMixMapperImpl implements ProductMixMapper {

    @Override
    public ProductMix toProductMix(ProductMixDto productMixDto) {
        if ( productMixDto == null ) {
            return null;
        }

        ProductMix.ProductMixBuilder productMix = ProductMix.builder();

        productMix.product( productMixDtoToProduct( productMixDto ) );
        productMix.ingredient( productMixDtoToIngredient( productMixDto ) );
        productMix.id( productMixDto.getId() );
        productMix.ingredientAmount( productMixDto.getIngredientAmount() );

        return productMix.build();
    }

    @Override
    public ProductMixDto toPpoductMixDto(ProductMix productMix) {
        if ( productMix == null ) {
            return null;
        }

        ProductMixDto productMixDto = new ProductMixDto();

        productMixDto.setProductId( productMixProductId( productMix ) );
        productMixDto.setProductName( productMixProductProductName( productMix ) );
        productMixDto.setIngredientId( productMixIngredientId( productMix ) );
        productMixDto.setIngredientName( productMixIngredientIngredientName( productMix ) );
        productMixDto.setId( productMix.getId() );
        productMixDto.setIngredientAmount( productMix.getIngredientAmount() );

        return productMixDto;
    }

    @Override
    public List<ProductMixDto> toProductsMixDto(List<ProductMix> productsMixes) {
        if ( productsMixes == null ) {
            return null;
        }

        List<ProductMixDto> list = new ArrayList<ProductMixDto>( productsMixes.size() );
        for ( ProductMix productMix : productsMixes ) {
            list.add( toPpoductMixDto( productMix ) );
        }

        return list;
    }

    protected Product productMixDtoToProduct(ProductMixDto productMixDto) {
        if ( productMixDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productMixDto.getProductId() );
        product.productName( productMixDto.getProductName() );

        return product.build();
    }

    protected Ingredient productMixDtoToIngredient(ProductMixDto productMixDto) {
        if ( productMixDto == null ) {
            return null;
        }

        Ingredient.IngredientBuilder ingredient = Ingredient.builder();

        ingredient.id( productMixDto.getIngredientId() );

        return ingredient.build();
    }

    private Long productMixProductId(ProductMix productMix) {
        if ( productMix == null ) {
            return null;
        }
        Product product = productMix.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productMixProductProductName(ProductMix productMix) {
        if ( productMix == null ) {
            return null;
        }
        Product product = productMix.getProduct();
        if ( product == null ) {
            return null;
        }
        String productName = product.getProductName();
        if ( productName == null ) {
            return null;
        }
        return productName;
    }

    private Long productMixIngredientId(ProductMix productMix) {
        if ( productMix == null ) {
            return null;
        }
        Ingredient ingredient = productMix.getIngredient();
        if ( ingredient == null ) {
            return null;
        }
        Long id = ingredient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productMixIngredientIngredientName(ProductMix productMix) {
        if ( productMix == null ) {
            return null;
        }
        Ingredient ingredient = productMix.getIngredient();
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
