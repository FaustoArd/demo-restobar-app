package com.lord.arbam.service_impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.repository.IngredientMixRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.service.IngredientMixService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMixServiceImpl implements IngredientMixService {

	@Autowired
	private final IngredientMixRepository ingredientMixRepository;
	
	@Autowired
	private final ProductRepository productRepository;
	
	private static final Logger log = LoggerFactory.getLogger(ProductMixServiceImpl.class);

	

	@Override
	public IngredientMix findIngredientMixById(Long id) {
		return ingredientMixRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Ingredient mix not found"));
	}

	@Transactional
	@Override
	public IngredientMix saveIngredientMix(IngredientMix ingredientMix) {
		Product product = productRepository.findById(ingredientMix.getProduct().getId()).orElseThrow(() -> new ItemNotFoundException("Product not found"));
		product.setMixed(true);
		productRepository.save(product);
		return ingredientMixRepository.save(ingredientMix);
	}

	@Override
	public IngredientMix updateIngredientMix(IngredientMix productMix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteIngredientMixById(Long id) {
		if(ingredientMixRepository.existsById(id)) {
			ingredientMixRepository.deleteById(id);
		}else {
			throw new ItemNotFoundException("Ingredient Mix not found");
		}
	

	}

	@Override
	public List<IngredientMix> findAllIngredientMixes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IngredientMix> findByProductId(Long id) {
		return (List<IngredientMix>) ingredientMixRepository.findByProductId(id).orElseThrow(() -> new ItemNotFoundException(
				"Ingredient Mix not found."));
	}

}
