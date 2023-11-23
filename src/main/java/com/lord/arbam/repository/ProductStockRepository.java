package com.lord.arbam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

	
	public Optional<ProductStock> findStockByProductId(Long id);
}
