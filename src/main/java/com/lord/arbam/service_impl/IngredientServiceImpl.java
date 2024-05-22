package com.lord.arbam.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lord.arbam.dto.IngredientStockDto;
import com.lord.arbam.exception.ItemNotFoundException;
import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.repository.IngredientRepository;
import com.lord.arbam.repository.IngredientMixRepository;
import com.lord.arbam.service.IngredientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private final IngredientRepository ingredientRepository;
	
	private static final Sort sort = Sort.by("ingredientName");

	@Autowired
	private final IngredientMixRepository ingredientMixRepository;
	
	private static Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

	

	@Override
	public Ingredient findIngredientById(Long id) {
		log.info("find ingredient by id");
		return ingredientRepository.findById(id).orElseThrow(
				() -> new ItemNotFoundException("No se encontro el ingrediente. RecipentServiceImpl.findRecipentById"));
	}

	@Override
	public Ingredient saveIngredient(IngredientCategory category, Ingredient ingredient) {
		log.info("saving ingredient");
		Ingredient savedIngredient = Ingredient.builder().ingredientName(ingredient.getIngredientName())
				.ingredientCategory(category).ingredientAmount(ingredient.getIngredientAmount())
				.ingredientAmount(ingredient.getIngredientAmount()).expirationDate(ingredient.getExpirationDate())
				.build();
		return ingredientRepository.save(savedIngredient);
	}

	@Override
	public Ingredient updateIngredient(IngredientCategory category, Ingredient ingredient) {
		log.info("updating ingredient");
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
		log.info("eliminando ingrediente");
		if (ingredientRepository.existsById(id)) {
			ingredientRepository.deleteById(id);
		} else {
			throw new ItemNotFoundException("No se encontro el ingrediente. IngredientServiceImpl.deleteById");
		}

	}

	/** This method find the ingredients assigned to the product, and subtract the ingredient amount by the aggregated product stock **/
	/**Ej: the manager add a pepperoni stock of 10, if a pizza consumes 300gr muzzarela  and 400gr of yeast,it discount 3000gr of muzzarela and 4000gr of yeast**/
	/*Throw Exception if the final ingredient amount is minor to zero */
	@Transactional
	@Override
	public void updateIngredientAmount(Integer stockCreated, Long productId) {
		log.info("Updating ingredient quantity after create product stock");
		List<IngredientMix> mixes = ingredientMixRepository.findByProductId(productId);
		ListIterator<IngredientMix> mixesIterator = mixes.listIterator();
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
	public void increaseIngredientAmount(Integer stockDeleted, Long productId) {
		log.info("Updating ingredient quantity after delete product stock");
		List<IngredientMix> mixes = ingredientMixRepository.findByProductId(productId);
		ListIterator<IngredientMix> mixesIterator = mixes.listIterator();
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		mixesIterator.forEachRemaining(mix -> {
			Ingredient ingredient = findIngredientById(mix.getIngredient().getId());
			ingredient
					.setIngredientAmount(ingredient.getIngredientAmount() + (stockDeleted * mix.getIngredientAmount()));
			ingredients.add(ingredient);
			
		});
		ingredientRepository.saveAll(ingredients);
		
	}
	

	@Override
	public List<Ingredient> findAllIngredients() {
		log.info("buscando todos los  ingredientes");
		return (List<Ingredient>)ingredientRepository.findAll();
	}

	@Override
	public List<Ingredient> findAllIngredientsOrderByNameAsc() {
		log.info("find all ingredients by name asc");
		return (List<Ingredient>)ingredientRepository.findAll(sort);
	}

	@Override
	public IngredientStockDto increaseIngredientStock(IngredientStockDto ingredientStockDto) {
		log.info("Increase ingredient stock");
		Ingredient ingredient = findIngredientById(ingredientStockDto.getId());
		ingredient.setIngredientAmount(ingredient.getIngredientAmount()+ingredientStockDto.getIngredientAmount());
		Ingredient saveIngredient = ingredientRepository.save(ingredient);
		return mapIngredientToStockDto(saveIngredient);
		
	}
	
	@Override
	public IngredientStockDto DecreaseIngredientStock(IngredientStockDto ingredientStockDto) {
		log.info("Decrease ingredient stock");
		Ingredient ingredient = findIngredientById(ingredientStockDto.getId());
		if(ingredient.getIngredientAmount()-ingredientStockDto.getIngredientAmount()<0) {
			throw new NegativeNumberException("No se puede realizar la operacion, el stock quedaria en negativo"); 
		}else {
			ingredient.setIngredientAmount(ingredient.getIngredientAmount()-ingredientStockDto.getIngredientAmount());
			Ingredient savedIngredient = ingredientRepository.save(ingredient);
			return mapIngredientToStockDto(savedIngredient);
		}
		
	}
	private IngredientStockDto mapIngredientToStockDto(Ingredient ingredient) {
		return new IngredientStockDto(ingredient.getId(),ingredient.getIngredientName(),ingredient.getIngredientAmount());
	}


	
}
