package com.lord.arbam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.IngredientMix;

public interface IngredientMixRepository extends JpaRepository<IngredientMix, Long> {

	
	public List<IngredientMix> findByProductIdOrderByIngredientIngredientNameAsc(Long id);
	
	public List<IngredientMix> findByProductId(Long id);
	
	public IngredientMix findByProductIdAndIngredientId(Long productId,Long ingredientId);
}
