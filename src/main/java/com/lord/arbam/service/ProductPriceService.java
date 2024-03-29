package com.lord.arbam.service;

import com.lord.arbam.model.ProductPrice;

public interface ProductPriceService {
	
	public ProductPrice savePrice(ProductPrice price);
	
	public ProductPrice findPriceById(Long id);
	
	public ProductPrice updatePrice(ProductPrice price);
	
	public ProductPrice findByProductId(Long id);

}
