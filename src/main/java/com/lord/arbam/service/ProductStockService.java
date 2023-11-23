package com.lord.arbam.service;

import com.lord.arbam.model.ProductStock;

public interface ProductStockService {
	
	public ProductStock saveStock(ProductStock productStock);
	
	public ProductStock findStockById(Long id);
	
	public ProductStock updateStock(ProductStock stock, Long  productId);
	
	public ProductStock findStockByProductId(Long productId);
	
	public void subTractStock(Integer amount,Long productId);
	
	

}
