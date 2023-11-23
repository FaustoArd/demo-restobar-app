package com.lord.arbam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
