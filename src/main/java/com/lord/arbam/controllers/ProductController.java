package com.lord.arbam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lord.arbam.dtos.ProductDto;
import com.lord.arbam.mappers.ProductMapper;
import com.lord.arbam.models.Product;
import com.lord.arbam.services.ProductService;


@RestController
@RequestMapping("/api/v1/arbam/products")
public class ProductController {

	@Autowired
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
		}

	@PostMapping("/")
	ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {

		Product product = ProductMapper.INSTANCE.toProduct(productDto);
		Product savedProduct = productService.saveProduct(product);
		ProductDto savedProductDto = ProductMapper.INSTANCE.toProductDto(savedProduct);
		return new ResponseEntity<ProductDto>(savedProductDto, HttpStatus.CREATED);

	}

	@PutMapping("/")
	ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
		Product product = ProductMapper.INSTANCE.toProduct(productDto);
		Product updatedProduct = productService.updateProduct(product);
		ProductDto updatedProductDto = ProductMapper.INSTANCE.toProductDto(updatedProduct);
		return new ResponseEntity<ProductDto>(updatedProductDto, HttpStatus.OK);

	}
}