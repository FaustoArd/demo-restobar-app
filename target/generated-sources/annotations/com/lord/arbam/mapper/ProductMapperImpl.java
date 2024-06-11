package com.lord.arbam.mapper;

import com.lord.arbam.dto.ProductDto;
import com.lord.arbam.dto.ProductStockDto;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T19:40:07-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 21 (Oracle Corporation)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.category( productDtoToProductCategory( productDto ) );
        product.productStock( productDtoToProductStock( productDto ) );
        product.productPrice( productDtoToProductPrice( productDto ) );
        product.id( productDto.getId() );
        product.mixed( productDto.isMixed() );
        product.productName( productDto.getProductName() );

        return product.build();
    }

    @Override
    public ProductDto toProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setCategoryId( productCategoryId( product ) );
        productDto.setCategoryName( productCategoryCategoryName( product ) );
        productDto.setProductStock( productProductStockProductStock( product ) );
        productDto.setProductPrice( productProductPricePrice( product ) );
        productDto.setId( product.getId() );
        productDto.setMixed( product.isMixed() );
        productDto.setProductName( product.getProductName() );

        return productDto;
    }

    @Override
    public List<ProductDto> toProductsDto(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( toProductDto( product ) );
        }

        return list;
    }

    @Override
    public ProductStock toStock(ProductStockDto productStockDto) {
        if ( productStockDto == null ) {
            return null;
        }

        ProductStock.ProductStockBuilder productStock = ProductStock.builder();

        productStock.product( productStockDtoToProduct( productStockDto ) );
        productStock.id( productStockDto.getId() );
        productStock.productStock( productStockDto.getProductStock() );

        return productStock.build();
    }

    @Override
    public ProductStockDto toStockDto(ProductStock productStock) {
        if ( productStock == null ) {
            return null;
        }

        ProductStockDto productStockDto = new ProductStockDto();

        productStockDto.setProductId( productStockProductId( productStock ) );
        productStockDto.setId( productStock.getId() );
        productStockDto.setProductStock( productStock.getProductStock() );

        return productStockDto;
    }

    protected ProductCategory productDtoToProductCategory(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductCategory.ProductCategoryBuilder productCategory = ProductCategory.builder();

        productCategory.id( productDto.getCategoryId() );
        productCategory.categoryName( productDto.getCategoryName() );

        return productCategory.build();
    }

    protected ProductStock productDtoToProductStock(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductStock.ProductStockBuilder productStock = ProductStock.builder();

        productStock.productStock( productDto.getProductStock() );

        return productStock.build();
    }

    protected ProductPrice productDtoToProductPrice(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductPrice.ProductPriceBuilder productPrice = ProductPrice.builder();

        productPrice.price( productDto.getProductPrice() );

        return productPrice.build();
    }

    private Long productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productCategoryCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductCategory category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String categoryName = category.getCategoryName();
        if ( categoryName == null ) {
            return null;
        }
        return categoryName;
    }

    private Integer productProductStockProductStock(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductStock productStock = product.getProductStock();
        if ( productStock == null ) {
            return null;
        }
        Integer productStock1 = productStock.getProductStock();
        if ( productStock1 == null ) {
            return null;
        }
        return productStock1;
    }

    private BigDecimal productProductPricePrice(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductPrice productPrice = product.getProductPrice();
        if ( productPrice == null ) {
            return null;
        }
        BigDecimal price = productPrice.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }

    protected Product productStockDtoToProduct(ProductStockDto productStockDto) {
        if ( productStockDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productStockDto.getProductId() );

        return product.build();
    }

    private Long productStockProductId(ProductStock productStock) {
        if ( productStock == null ) {
            return null;
        }
        Product product = productStock.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
