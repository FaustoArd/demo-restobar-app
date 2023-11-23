package com.lord.arbam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> findByCategoryId(Long id);

}
