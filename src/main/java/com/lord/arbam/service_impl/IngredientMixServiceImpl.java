package com.lord.arbam.service_impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.ValueAlreadyExistException;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.repository.IngredientMixRepository;
import com.lord.arbam.repository.IngredientRepository;
import com.lord.arbam.repository.ProductRepository;
import com.lord.arbam.service.IngredientMixService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientMixServiceImpl implements IngredientMixService {

	@Autowired
	private final IngredientMixRepository ingredientMixRepository;

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final IngredientRepository ingredientRepository;

	private static final Logger log = LoggerFactory.getLogger(IngredientMixServiceImpl.class);

	@Override
	public IngredientMix findIngredientMixById(Long id) {
		return ingredientMixRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Ingredient mix not found"));
	}

	@Transactional
	@Override
	public IngredientMix saveIngredientMix(IngredientMix ingredientMix, Long productId) {
		if (findByProductId(productId).stream().filter(mix -> mix.getIngredient().getIngredientName()
				.equals(ingredientMix.getIngredient().getIngredientName())).findFirst().isPresent()) {
			log.warn("Ese ingrediente ya existe en esta receta");
			throw new ValueAlreadyExistException("Ingredient already exist in this recipe");
		} else {

			log.info("Agregando Ingrediente a la receta");
			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new ItemNotFoundException("Product not found"));
			Ingredient ingredient = ingredientRepository.findById(ingredientMix.getIngredient().getId())
					.orElseThrow(() -> new ItemNotFoundException("Ingredient not found"));
			ingredientMix.setIngredient(ingredient);
			ingredientMix.setProduct(product);
			product.setMixed(true);
			productRepository.save(product);
			return ingredientMixRepository.save(ingredientMix);
		}

	}

	@Override
	public IngredientMix updateIngredientMix(IngredientMix productMix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteIngredientMixById(Long id) {

		if (ingredientMixRepository.existsById(id)) {
			log.info("Eliminando item de receta");
			ingredientMixRepository.deleteById(id);
		} else {
			log.info("No se encontro el ingrediente");
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

		return (List<IngredientMix>) ingredientMixRepository.findByProductId(id)
				.orElseThrow(() -> new ItemNotFoundException("Ingredient Mix not found."));
	}

	@Override
	public List<IngredientMix> findByProductIdByOrderAsc(Long id) {

		return (List<IngredientMix>) ingredientMixRepository.findByProductIdOrderByIngredientIngredientNameAsc(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro la receta"));
	}

}
