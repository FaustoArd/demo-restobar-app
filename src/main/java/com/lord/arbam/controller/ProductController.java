package com.lord.arbam.controller;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.lord.arbam.dto.IngredientStockUpdateReportDto;
import com.lord.arbam.dto.PriceUpdateReportDto;
import com.lord.arbam.dto.ProductDto;
import com.lord.arbam.dto.ProductStockDto;
import com.lord.arbam.dto.ProductStockUpdateReportDto;
import com.lord.arbam.mapper.ProductMapper;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductPriceService;
import com.lord.arbam.service.ProductService;
import com.lord.arbam.service.ProductStockService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/products")
@RequiredArgsConstructor
public class ProductController {

	@Autowired
	private final ProductService productService;

	@Autowired
	private final ProductStockService productStockService;

	@Autowired
	private final IngredientService ingredientService;
	
	@Autowired
	private final ProductPriceService priceService;

	private static final Gson gson = new Gson();

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@GetMapping("/{id}")
	ResponseEntity<ProductDto> findProductById(@PathVariable("id") Long id) {
		log.info("Buscando product por id");
		Product findedProduct = productService.findProductById(id);
		ProductDto findedProductDto = ProductMapper.INSTANCE.toProductDto(findedProduct);
		return new ResponseEntity<ProductDto>(findedProductDto, HttpStatus.OK);
	}

	@GetMapping("/all")
	ResponseEntity<List<ProductDto>> findAllProducts() {
		log.info("Buscando todos los productos");
		List<Product> products = productService.findAllProducts();
		List<ProductDto> productsDto = ProductMapper.INSTANCE.toProductsDto(products);
		return new ResponseEntity<List<ProductDto>>(productsDto, HttpStatus.OK);
	}

	@GetMapping("/all_asc")
	ResponseEntity<List<ProductDto>> findAllProductsByProductNameAsc() {
		log.info("Buscando todos los productos");
		List<Product> products = productService.findAllProductByProductNameOrderAsc();
		List<ProductDto> productsDto = ProductMapper.INSTANCE.toProductsDto(products);
		return new ResponseEntity<List<ProductDto>>(productsDto, HttpStatus.OK);
	}

	@GetMapping("/all_by_category/{id}")
	ResponseEntity<List<ProductDto>> findAllProductsByCategoryId(@PathVariable("id") Long id) {
		log.info("Buscando todos los productos por categoria");
		List<Product> products = productService.findByCategoryId(id);
		List<ProductDto> productsDto = ProductMapper.INSTANCE.toProductsDto(products);
		return new ResponseEntity<List<ProductDto>>(productsDto, HttpStatus.OK);
	}

	@PostMapping("/")
	ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
		log.info("Guardando todos los productos");
		Product product = ProductMapper.INSTANCE.toProduct(productDto);
		Product savedProduct = productService.saveProduct(product);
		ProductDto savedProductDto = ProductMapper.INSTANCE.toProductDto(savedProduct);
		return new ResponseEntity<ProductDto>(savedProductDto, HttpStatus.CREATED);

	}

	@PutMapping("/")
	ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
		log.info("Acuatizando producto");
		Product product = ProductMapper.INSTANCE.toProduct(productDto);
		Product updatedProduct = productService.updateProduct(product);
		ProductDto updatedProductDto = ProductMapper.INSTANCE.toProductDto(updatedProduct);
		return new ResponseEntity<ProductDto>(updatedProductDto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
		Product findedProduct = productService.findProductById(id);
		productService.deleteProductById(id);
		return new ResponseEntity<String>(gson.toJson("Se elimino el producto:" + findedProduct.getProductName()),
				HttpStatus.OK);
	}

	@PostMapping("/create_stock")
	ResponseEntity<ProductStockUpdateReportDto> createStock(@RequestParam("productId") Long productId,
			@RequestBody ProductStockDto stockDto) {
		log.info("Buscando producto por id");
		Product product = productService.findProductById(productId);
		int oldProductStock = productStockService.findStockByProductId(product.getId()).getProductStock();
		
		if (product.isMixed()) {
			log.info("Actualizando la cantidad de ingredientes");
			List<IngredientStockUpdateReportDto> ingredientReportDtos = ingredientService.decreaseIngredientAmount(stockDto.getProductStock(), productId);
			ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
			log.info("Creando o actualizando stock");
			ProductStock savedStock = productStockService.updateStock(stock, productId);
			log.info("Guardando producto");
			Product productUpdated = productService.createProductStock(product, savedStock);
			
			ProductStockUpdateReportDto report = mapToProductUpdateReportDto(productUpdated, oldProductStock, savedStock, ingredientReportDtos);
			return new ResponseEntity<ProductStockUpdateReportDto>(report, HttpStatus.CREATED);
		}
		ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
		log.info("Creando o actualizando stock");
		ProductStock savedStock = productStockService.updateStock(stock, productId);
		log.info("Guardando producto");
		Product productUpdated = productService.createProductStock(product, savedStock);
		ProductStockUpdateReportDto report = mapToProductUpdateReportDto
				(productUpdated, oldProductStock, savedStock, new ArrayList<IngredientStockUpdateReportDto>());
		return new ResponseEntity<ProductStockUpdateReportDto>(report, HttpStatus.CREATED);

	}
	
	@PutMapping("/reduce_stock")
	ResponseEntity<ProductStockUpdateReportDto> reduceStock(@RequestParam("productId") Long productId,
			@RequestBody ProductStockDto stockDto) {
		Product product = productService.findProductById(productId);
		int oldProductStock = productStockService.findStockByProductId(product.getId()).getProductStock();
		ProductStock stock = ProductMapper.INSTANCE.toStock(stockDto);
		log.info("Eliminando stock");
		ProductStock savedStock = productStockService.reduceStock(stock, productId);
		if (product.isMixed()) {
			log.info("Actualizando la cantidad de ingredientes");
			List<IngredientStockUpdateReportDto> ingredientReportDtos = ingredientService.increaseIngredientAmount(stockDto.getProductStock(), productId);
			ProductStockUpdateReportDto report = mapToProductUpdateReportDto(product, oldProductStock, savedStock, ingredientReportDtos);
			return new ResponseEntity<ProductStockUpdateReportDto>(report, HttpStatus.OK);
			
		}
		ProductStockUpdateReportDto report = mapToProductUpdateReportDto(product, oldProductStock, savedStock,  new ArrayList<IngredientStockUpdateReportDto>());
		return new ResponseEntity<ProductStockUpdateReportDto>(report, HttpStatus.OK);

	}
	private static ProductStockUpdateReportDto mapToProductUpdateReportDto
	(Product product,int oldProductStock,ProductStock savedStock,List<IngredientStockUpdateReportDto> ingredientReportDtos) {
		ProductStockUpdateReportDto productStockUpdateReportDto = new ProductStockUpdateReportDto();
		productStockUpdateReportDto.setProductName(product.getProductName());
		productStockUpdateReportDto.setProductOldQuantity(oldProductStock);
		productStockUpdateReportDto.setProductNewQuantity(savedStock.getProductStock());
		productStockUpdateReportDto.setIngrdientStockReports(ingredientReportDtos);
		return productStockUpdateReportDto;
	}
	
	
	@PutMapping("/update-by-percentage")
	ResponseEntity<List<PriceUpdateReportDto>> updatePriceByPercentageByCategory
	(@RequestParam("categoryId")long categoryId,@RequestBody double percentage,@RequestParam("positive")boolean positive){
		List<Long> productIds = productService.findProductIdsByCategory(categoryId);
		List<PriceUpdateReportDto> priceReports = priceService.updateAllPriceByPercentageByCategory(productIds, percentage, positive);
		return new ResponseEntity<List<PriceUpdateReportDto>>(priceReports,HttpStatus.OK);
	}
}
