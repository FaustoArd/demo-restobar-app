package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductStock;

public interface ProductService {

	public Product findProductById(Long id);
	
	public Product saveProduct(Product product);
	
	public Product updateProduct(Product product);
	
	public void deleteProductById(Long id);
	
	public Product createProductStock(Product product,ProductStock stock);
	
	public List<Product> findAllProducts();
	
	public List<Product> findByCategoryId(Long id);
}
