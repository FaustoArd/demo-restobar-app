package com.lord.arbam.services;

import com.lord.arbam.models.ProductStock;

public interface ProductStockService {
	
	public ProductStock saveStock(ProductStock productStock);
	
	public ProductStock findStockById(Long id);
	
	public ProductStock updateStock(ProductStock stock, Long  productId);
	
	public ProductStock findStockByProductId(Long productId);
	
	public void subTractStock(Integer amount,Long productId);
	
	

}
