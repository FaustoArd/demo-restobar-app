package com.lord.arbam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
	
	public Optional<ProductPrice> findByProductId(Long id);

}
