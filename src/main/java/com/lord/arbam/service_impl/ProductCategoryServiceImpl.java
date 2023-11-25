package com.lord.arbam.service_impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.repository.ProductCategoryRepository;
import com.lord.arbam.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class  ProductCategoryServiceImpl implements CategoryService<ProductCategory> {

	private static Logger log = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	
	@Autowired
	private final ProductCategoryRepository productCategoryRepository;

	@Override
	public ProductCategory findCategoryById(Long id) {
		return productCategoryRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Category Not found"));
	}

	@Override
	public ProductCategory saveCategory(ProductCategory category) {
		log.info("Creando nueva categoria");
		return productCategoryRepository.save(category);
	}

	@Override
	public ProductCategory updateCategory(ProductCategory category) {
		return productCategoryRepository.findById(category.getId()).map(cat -> {
			cat.setCategoryName(category.getCategoryName());
			return productCategoryRepository.save(cat);
		}).orElseThrow(() -> new ItemNotFoundException("Category Not found"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		if (productCategoryRepository.existsById(id)) {
			productCategoryRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("Category Not found");
		}

		
	}

	@Override
	public List<ProductCategory> findAllCategories() {
		return (List<ProductCategory>) productCategoryRepository.findAll();
	}

	@Override
	public List<ProductCategory> findAllCategoriesOrderByNamAsc() {
		Sort sort = Sort.by("categoryName");
		return (List<ProductCategory>)productCategoryRepository.findAll(sort);
	 
	}

	
}
