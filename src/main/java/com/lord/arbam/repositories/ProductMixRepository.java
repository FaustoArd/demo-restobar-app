package com.lord.arbam.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.ProductMix;

public interface ProductMixRepository extends JpaRepository<ProductMix, Long> {

	
	public Optional<List<ProductMix>> findByProductId(Long id);
}
