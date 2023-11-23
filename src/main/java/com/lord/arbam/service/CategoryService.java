package com.lord.arbam.service;

import java.util.List;

public interface  CategoryService<T> {
	
	public  T findCategoryById(Long id);
	
	public  T saveCategory(T category);
	
	public  T updateCategory(T category);
	
	public void deleteCategoryById(Long id);
	
	public List<T> findAllCategories();

}
