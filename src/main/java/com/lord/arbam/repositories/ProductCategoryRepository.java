package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lord.arbam.models.ProductCategory;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{

}
