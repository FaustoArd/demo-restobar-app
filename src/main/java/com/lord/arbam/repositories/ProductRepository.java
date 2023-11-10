package com.lord.arbam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lord.arbam.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> findByCategoryId(Long id);

}
