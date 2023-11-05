package com.lord.arbam.services;

import com.lord.arbam.models.ProductStock;

public interface ProductStockService {
	
	public ProductStock saveProductStock(ProductStock productStock);
	
	public ProductStock findProductStockById(Long id);
	
	public ProductStock updateProductStock(ProductStock stock, Long  productId);
	
	public ProductStock findStockByProductId(Long id);
	
	

}
