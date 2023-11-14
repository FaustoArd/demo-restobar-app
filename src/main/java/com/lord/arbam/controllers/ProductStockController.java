package com.lord.arbam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.services.ProductStockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/product_stock")
@RequiredArgsConstructor
public class ProductStockController {
	
	@Autowired
	private final ProductStockService productStockService;

	
	
}
