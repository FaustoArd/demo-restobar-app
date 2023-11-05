package com.lord.arbam.services;

import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductStock;

public interface ProductService {

	public Product findProductById(Long id);
	
	public Product saveProduct(Product product);
	
	public Product updateProduct(Product product);
	
	public void deleteProductById(Long id);
	
	public Product createProductStock(Product product,ProductStock stock);
}
