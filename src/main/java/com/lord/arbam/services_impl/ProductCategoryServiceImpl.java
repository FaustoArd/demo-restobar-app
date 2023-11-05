package com.lord.arbam.services_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.repositories.ProductCategoryRepository;
import com.lord.arbam.services.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private final ProductCategoryRepository productCategoryRepository;

	
	public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public ProductCategory findCategoryById(Long id) {
		return productCategoryRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro la categoria."));
	}

	@Override
	public ProductCategory saveCategory(ProductCategory category) {
		return productCategoryRepository.save(category);
	}

	@Override
	public ProductCategory updateCategory(ProductCategory category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategoryById(Long id) {
		// TODO Auto-generated method stub

	}
}
