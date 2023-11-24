package com.lord.arbam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.ProductDto;
import com.lord.arbam.dto.ProductStockDto;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductStock;

@Mapper
public interface ProductMapper {
	
	public static ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	
	@Mapping(target="category.id", source="categoryId")
	@Mapping(target="category.categoryName", source="categoryName")
	@Mapping(target="productStock.productStock", source="productStock")
	@Mapping(target="productPrice.price", source="productPrice")
	public Product toProduct(ProductDto productDto);
	
	
	@Mapping(target="categoryId", source="category.id")
	@Mapping(target="categoryName", source="category.categoryName")
	@Mapping(target="productStock", source="productStock.productStock")
	@Mapping(target="productPrice", source="productPrice.price")
	public ProductDto toProductDto(Product product);
	
	@Mapping(target="categoryId", source="category.id")
	@Mapping(target="categoryName", source="category.categoryName")
	@Mapping(target="productStock", source="productStock.productStock")
	@Mapping(target="productPrice", source="productPrice.productPrice")
	public List<ProductDto> toProductsDto(List<Product> products);
	
	
	@Mapping(target="product.id", source="productId")
	public ProductStock toStock(ProductStockDto productStockDto);
	
	@Mapping(target="productId", source = "product.id")
	public ProductStockDto toStockDto(ProductStock productStock);
}
