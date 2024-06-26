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
		log.info("Find ingredient mix by id.");
		return ingredientMixRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro la receta"));
	}

	@Transactional
	@Override
	public IngredientMix saveIngredientMix(IngredientMix ingredientMix, Long productId) {
		if (findByProductId(productId).stream().filter(mix -> mix.getIngredient().getIngredientName()
				.equals(ingredientMix.getIngredient().getIngredientName())).findFirst().isPresent()) {
			log.warn("Ingredient already exist in this recipe");
			throw new ValueAlreadyExistException("Ese ingrediente ya existe en esta receta");
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

	@Transactional
	@Override
	public void deleteIngredientMixById(Long id) {

		if (ingredientMixRepository.existsById(id)) {
			setIngredientMixDeleted(id);
		} else {
			log.info("Ingredient Mix not found");
			throw new ItemNotFoundException("No se encontro el ingrediente");
		}

	}
	
	private void setIngredientMixDeleted(long ingredientMixId) {
		long productId = findIngredientMixById(ingredientMixId).getProduct().getId();
		long count = ingredientMixRepository.findByProductId(productId).stream().count();
		if (count == 1) {
			log.info("El producto no contiene ningun item de receta, seteando product.mixed a false");
			Product product = findProductById(productId);
			product.setMixed(false);
			productRepository.save(product);
			log.info("Eliminando item de receta");
			ingredientMixRepository.deleteById(ingredientMixId);
			
		} else {
		log.info("El producto todavia contiene mas de un ingrediente.");
			log.info("Eliminando item de receta");
			ingredientMixRepository.deleteById(ingredientMixId);
		}
	}

	@Override
	public List<IngredientMix> findAllIngredientMixes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IngredientMix> findByProductId(Long id) {

		return (List<IngredientMix>) ingredientMixRepository.findByProductId(id);

	}

	@Override
	public List<IngredientMix> findByProductIdByOrderAsc(Long id) {
		return (List<IngredientMix>) ingredientMixRepository.findByProductIdOrderByIngredientIngredientNameAsc(id);

	}

	private Product findProductById(long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ItemNotFoundException("No se encontro el producto."));
	}

}
