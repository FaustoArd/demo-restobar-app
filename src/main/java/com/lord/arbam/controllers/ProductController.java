package com.lord.arbam.controllers;

import java.util.List;

import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lord.arbam.dtos.ProductDto;
import com.lord.arbam.dtos.ProductStockDto;
import com.lord.arbam.mappers.ProductMapper;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.services.ProductService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/products")
@RequiredArgsConstructor
public class ProductController {

	@Autowired
	private final ProductService productService;
	
	private static final Gson gson = new Gson();
	
	

	@GetMapping("/{id}")
	ResponseEntity<ProductDto> findProductById(@PathVariable("id")Long id){
		Product findedProduct = productService.findProductById(id);
		ProductDto findedProductDto = ProductMapper.INSTANCE.toProductDto(findedProduct);
		return new ResponseEntity<ProductDto>(findedProductDto,HttpStatus.OK);
	}
	

	@GetMapping("/all")
	ResponseEntity<List<ProductDto>> findAllProducts() {
		List<Product> products = productService.findAllProducts();
		List<ProductDto> productsDto = ProductMapper.INSTANCE.toProductsDto(products);
		return new ResponseEntity<List<ProductDto>>(productsDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/all_by_category/{id}")
	ResponseEntity<List<ProductDto>> findAllProductsByCategoryId(@PathVariable("id")Long id){
		List<Product> products = productService.findByCategoryId(id);
		List<ProductDto> productsDto = ProductMapper.INSTANCE.toProductsDto(products);
		return new ResponseEntity<List<ProductDto>>(productsDto,HttpStatus.OK);
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
	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteProduct(@PathVariable("id")Long id){
		Product findedProduct = productService.findProductById(id);
		productService.deleteProductById(id);
		return new ResponseEntity<String>(gson.toJson("Se elimino el producto:" + findedProduct.getProductName()),HttpStatus.OK);
	}
	
	@PostMapping("/create_stock")
	ResponseEntity<ProductDto> createStock(@RequestParam("productId")Long productId, @RequestBody ProductStockDto stockDto){
		Product product = productService.findProductById(productId);
		ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
		Product productUpdated = productService.createProductStock(product, stock);
		ProductDto newProductDto =ProductMapper.INSTANCE.toProductDto(productUpdated);
		return new ResponseEntity<ProductDto>(newProductDto,HttpStatus.CREATED);
		
	}
}
