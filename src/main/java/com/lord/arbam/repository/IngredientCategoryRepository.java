package com.lord.arbam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

}
