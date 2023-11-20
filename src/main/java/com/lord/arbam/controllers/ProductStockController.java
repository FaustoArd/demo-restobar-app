package com.lord.arbam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.ProductStockDto;
import com.lord.arbam.mappers.ProductMapper;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.services.ProductStockService;
import com.nimbusds.jose.shaded.gson.Gson;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/product_stock")
@RequiredArgsConstructor
public class ProductStockController {
	
	@Autowired
	private final ProductStockService productStockService;
	private static final Gson gson = new Gson();
	
	
	@PostMapping("/create")
	ResponseEntity<?> createStock(@RequestParam("productId")Long productId,@RequestParam("stock")Integer stock){
		Product product = Product.builder().id(productId).build();
		ProductStock newStock = ProductStock.builder().productStock(stock).build();
		ProductStock savedStock = productStockService.saveStock(newStock);
		return new ResponseEntity<String>(gson.toJson("Stock creado: " +savedStock.getProductStock()),HttpStatus.CREATED);
		
	}
	@PostMapping("/update")
	 ResponseEntity<ProductStockDto> udpateStock(@RequestBody ProductStockDto productStockDto,@RequestParam("productId")Long productId){
		ProductStock stock = ProductMapper.INSTANCE.toStock(productStockDto);
		ProductStock newStock = productStockService.updateStock(stock, productId);
		ProductStockDto stockDto = ProductMapper.INSTANCE.toStockDto(newStock);
		return new ResponseEntity<ProductStockDto>(stockDto,HttpStatus.CREATED);
	}

	
	
}
