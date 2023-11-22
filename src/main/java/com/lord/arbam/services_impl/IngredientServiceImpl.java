package com.lord.arbam.services_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.exceptions.NegativeNumberException;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.models.ProductMix;
import com.lord.arbam.repositories.IngredientRepository;
import com.lord.arbam.repositories.ProductMixRepository;
import com.lord.arbam.services.IngredientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private final IngredientRepository ingredientRepository;

	@Autowired
	private final ProductMixRepository productMixRepository;
	
	private static Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

	

	@Override
	public Ingredient findIngredientById(Long id) {
		log.info("Buscando ingrediento por id");
		return ingredientRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("No se encontro el ingrediente. RecipentServiceImpl.findRecipentById"));
	}

	@Override
	public Ingredient saveIngredient(IngredientCategory category, Ingredient ingredient) {
		log.info("guardando ingrediente");
		Ingredient savedIngredient = Ingredient.builder().ingredientName(ingredient.getIngredientName())
				.ingredientCategory(category).ingredientAmount(ingredient.getIngredientAmount())
				.ingredientAmount(ingredient.getIngredientAmount()).expirationDate(ingredient.getExpirationDate())
				.build();
		return ingredientRepository.save(savedIngredient);
	}

	@Override
	public Ingredient updateIngredient(IngredientCategory category, Ingredient ingredient) {
		log.info("actualizando ingrediente");
		return ingredientRepository.findById(ingredient.getId()).map(updatedIngredient -> {
			updatedIngredient.setIngredientName(ingredient.getIngredientName());
			updatedIngredient.setIngredientCategory(category);
			updatedIngredient.setIngredientAmount(ingredient.getIngredientAmount());
			updatedIngredient.setExpirationDate(ingredient.getExpirationDate());

			return ingredientRepository.save(updatedIngredient);
		}).orElseThrow(() -> new ItemNotFoundException("No se encontro el ingrediente"));

	}

	@Override
	public void deleteIngredientById(Long id) {
		if (ingredientRepository.existsById(id)) {
			ingredientRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("No se encontro el ingrediente. IngredientServiceImpl.deleteById");
		}

	}

	/** This method find the ingredients assigned to the product, and subtract the ingredient amount by the aggregated product stock **/
	/**Ej: the manager add a pepperoni stock of 50, if a pizza consumes 300gr muzzarela  and 400gr of yeast the discounted form the ingredient stock   **/
	@Transactional
	@Override
	public void updateIngredientAmount(Integer stockCreated, Long productId) {
		log.info("Actualizando ingredientes despues de producir stock");
		List<ProductMix> mixes = productMixRepository.findByProductId(productId).orElseThrow(()-> new ItemNotFoundException("No se encontro la receta"));
		ListIterator<ProductMix> mixesIterator = mixes.listIterator();
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		mixesIterator.forEachRemaining(mix -> {
			Ingredient ingredient = findIngredientById(mix.getIngredient().getId());
			if(ingredient.getIngredientAmount() - (stockCreated * mix.getIngredientAmount())<0) {
				log.warn("La cantidad restante de ingredientes era menor a 0");
				throw new NegativeNumberException("Not enough ingredient amount to produce that stock");
			}else {
				log.info("Guardando la cantidad restante de ingredientes");
			ingredient
					.setIngredientAmount(ingredient.getIngredientAmount() - (stockCreated * mix.getIngredientAmount()));
			ingredients.add(ingredient);
			}
		});
		ingredientRepository.saveAll(ingredients);

	}

	@Override
	public List<Ingredient> findAllIngredients() {
		return (List<Ingredient>)ingredientRepository.findAll();
	}
}
