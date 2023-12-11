package com.lord.arbam.mapper;

import com.lord.arbam.dto.IngredientCategoryDto;
import com.lord.arbam.dto.ProductCategoryDto;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-08T14:23:57-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public ProductCategory toProductCategory(ProductCategoryDto productCategoryDto) {
        if ( productCategoryDto == null ) {
            return null;
        }

        ProductCategory.ProductCategoryBuilder productCategory = ProductCategory.builder();

        productCategory.categoryName( productCategoryDto.getCategoryName() );
        productCategory.id( productCategoryDto.getId() );

        return productCategory.build();
    }

    @Override
    public ProductCategoryDto toProductCategoryDto(ProductCategory productCategory) {
        if ( productCategory == null ) {
            return null;
        }

        ProductCategoryDto productCategoryDto = new ProductCategoryDto();

        productCategoryDto.setCategoryName( productCategory.getCategoryName() );
        productCategoryDto.setId( productCategory.getId() );

        return productCategoryDto;
    }

    @Override
    public List<ProductCategoryDto> toProductCategoriesDto(List<ProductCategory> categories) {
        if ( categories == null ) {
            return null;
        }

        List<ProductCategoryDto> list = new ArrayList<ProductCategoryDto>( categories.size() );
        for ( ProductCategory productCategory : categories ) {
            list.add( toProductCategoryDto( productCategory ) );
        }

        return list;
    }

    @Override
    public IngredientCategory toIngredientCategory(IngredientCategoryDto ingredientCategoryDto) {
        if ( ingredientCategoryDto == null ) {
            return null;
        }

        IngredientCategory.IngredientCategoryBuilder ingredientCategory = IngredientCategory.builder();

        ingredientCategory.categoryName( ingredientCategoryDto.getCategoryName() );
        ingredientCategory.id( ingredientCategoryDto.getId() );

        return ingredientCategory.build();
    }

    @Override
    public IngredientCategoryDto toIngredientCategoryDto(IngredientCategory ingredientCategory) {
        if ( ingredientCategory == null ) {
            return null;
        }

        IngredientCategoryDto ingredientCategoryDto = new IngredientCategoryDto();

        ingredientCategoryDto.setCategoryName( ingredientCategory.getCategoryName() );
        ingredientCategoryDto.setId( ingredientCategory.getId() );

        return ingredientCategoryDto;
    }

    @Override
    public List<IngredientCategoryDto> toIngredientCategoriesDto(List<IngredientCategory> categories) {
        if ( categories == null ) {
            return null;
        }

        List<IngredientCategoryDto> list = new ArrayList<IngredientCategoryDto>( categories.size() );
        for ( IngredientCategory ingredientCategory : categories ) {
            list.add( toIngredientCategoryDto( ingredientCategory ) );
        }

        return list;
    }
}
