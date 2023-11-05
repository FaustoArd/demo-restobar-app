package com.lord.arbam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
