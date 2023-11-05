package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lord.arbam.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	

}
