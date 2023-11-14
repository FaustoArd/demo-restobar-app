package com.lord.arbam.mappers;

import com.lord.arbam.dtos.ProductCategoryDto;
import com.lord.arbam.models.ProductCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-14T17:05:41-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class ProductCategoryMapperImpl implements ProductCategoryMapper {

    @Override
    public List<ProductCategoryDto> toCategoriesDto(List<ProductCategory> categories) {
        if ( categories == null ) {
            return null;
        }

        List<ProductCategoryDto> list = new ArrayList<ProductCategoryDto>( categories.size() );
        for ( ProductCategory productCategory : categories ) {
            list.add( productCategoryToProductCategoryDto( productCategory ) );
        }

        return list;
    }

    protected ProductCategoryDto productCategoryToProductCategoryDto(ProductCategory productCategory) {
        if ( productCategory == null ) {
            return null;
        }

        ProductCategoryDto productCategoryDto = new ProductCategoryDto();

        productCategoryDto.setCategoryName( productCategory.getCategoryName() );
        productCategoryDto.setId( productCategory.getId() );

        return productCategoryDto;
    }
}
