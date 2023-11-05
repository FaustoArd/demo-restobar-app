package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

}
