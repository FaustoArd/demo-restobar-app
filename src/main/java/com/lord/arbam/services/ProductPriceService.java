package com.lord.arbam.services;

import com.lord.arbam.models.ProductPrice;

public interface ProductPriceService {
	
	public ProductPrice save(ProductPrice price);
	
	public ProductPrice findById(Long id);
	
	public ProductPrice updatePrice(ProductPrice price);

}
