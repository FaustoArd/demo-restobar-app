package com.lord.arbam.service;

import java.util.List;

import com.lord.arbam.dto.IngredientStockReportDto;
import com.lord.arbam.dto.ProductStockDto;
import com.lord.arbam.dto.ProductStockUpdateReportDto;
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
	
	public List<Long> findProductIdsByCategory(Long categoryId);
	
	public List<Product> findAllProductByProductNameOrderAsc();
	
	public ProductStockUpdateReportDto increaseProductStock(long productId,ProductStockDto stockDto);
	
	public ProductStockUpdateReportDto decreaseProductStock(long productId,ProductStockDto stockDto);
	
	public IngredientStockReportDto checkIngredientsStock(long productId, int stock);
		

}
