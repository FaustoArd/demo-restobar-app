package com.lord.arbam.mappers;

import com.lord.arbam.dtos.ProductCategoryDto;
import com.lord.arbam.models.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-18T10:00:54-0400",
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
    public List<ProductCategoryDto> toCategoriesDto(List<ProductCategory> categories) {
        if ( categories == null ) {
            return null;
        }

        List<ProductCategoryDto> list = new ArrayList<ProductCategoryDto>( categories.size() );
        for ( ProductCategory productCategory : categories ) {
            list.add( toProductCategoryDto( productCategory ) );
        }

        return list;
    }
}
