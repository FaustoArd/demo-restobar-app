package com.lord.arbam.services;



import java.util.List;

import com.lord.arbam.models.ProductCategory;

public interface ProductCategoryService {
	
	public ProductCategory findCategoryById(Long id);
	
	public ProductCategory saveCategory(ProductCategory category);
	
	public ProductCategory updateCategory(ProductCategory category);
	
	public void deleteCategoryById(Long id);
	
	public List<ProductCategory> findAllCategories();

}
