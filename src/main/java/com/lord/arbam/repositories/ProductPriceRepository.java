package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

}
