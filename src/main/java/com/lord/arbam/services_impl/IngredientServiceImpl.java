package com.lord.arbam.services_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lord.arbam.exceptions.ItemNotFoundException;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.models.ProductMix;
import com.lord.arbam.repositories.IngredientRepository;
import com.lord.arbam.repositories.ProductMixRepository;
import com.lord.arbam.services.IngredientService;
import com.lord.arbam.services.ProductMixService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private final IngredientRepository ingredientRepository;

	@Autowired
	private final ProductMixRepository productMixRepository;

	

	@Override
	public Ingredient findIngredientById(Long id) {
		return ingredientRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("No se encontro el ingrediente. RecipentServiceImpl.findRecipentById"));
	}

	@Override
	public Ingredient saveIngredient(IngredientCategory category, Ingredient ingredient) {
		Ingredient savedIngredient = Ingredient.builder().ingredientName(ingredient.getIngredientName())
				.ingredientCategory(category).ingredientAmount(ingredient.getIngredientAmount())
				.ingredientAmount(ingredient.getIngredientAmount()).expirationDate(ingredient.getExpirationDate())
				.build();
		return ingredientRepository.save(savedIngredient);
	}

	@Override
	public Ingredient updateIngredient(IngredientCategory category, Ingredient ingredient) {

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

	@Transactional
	@Override
	public void updateIngredientAmount(Integer stockCreated, Long productId) {
		List<ProductMix> mixes = productMixRepository.findByProductId(productId).orElseThrow(()-> new ItemNotFoundException("No se encontro la receta"));
		ListIterator<ProductMix> it = mixes.listIterator();
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		it.forEachRemaining(mix -> {
			Ingredient ingredient = findIngredientById(mix.getIngredient().getId());
			ingredient
					.setIngredientAmount(ingredient.getIngredientAmount() - (stockCreated * mix.getIngredientAmount()));
			ingredients.add(ingredient);
		});
		ingredientRepository.saveAll(ingredients);

	}
}
