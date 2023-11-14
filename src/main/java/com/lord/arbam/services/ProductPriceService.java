package com.lord.arbam.services;

import com.lord.arbam.models.ProductPrice;

public interface ProductPriceService {
	
	public ProductPrice savePrice(ProductPrice price);
	
	public ProductPrice findPriceById(Long id);
	
	public ProductPrice updatePrice(ProductPrice price);
	
	public ProductPrice findByProductId(Long id);

}
