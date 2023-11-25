package com.lord.arbam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lord.arbam.dto.IngredientMixDto;
import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.exception.ValueAlreadyExistException;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.IngredientMixService;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IngredientMixServiceTest {
	
	
	@Autowired
	private IngredientMixService ingredientMixService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private CategoryService<ProductCategory> pruductCategoryService;
	
	@Autowired
	private CategoryService<IngredientCategory> ingredientCategoryService;

	
	@Test
	@Order(1)
	void setup() {
		
		ProductCategory pCategory1 = ProductCategory.builder().categoryName("Pizza").build();
		pruductCategoryService.saveCategory(pCategory1);
		
		Product product1 = Product.builder().productName("Grande Muzza").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(1500.00))).build();
		Product savedProduct1 = productService.saveProduct(product1);

		Product product2 = Product.builder().productName("Grande Cebolla").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(1800.00))).build();
		Product savedProduct2 = productService.saveProduct(product2);
		
		IngredientCategory ingredientCategory3 = IngredientCategory.builder().categoryName("Especias").build();
		IngredientCategory savedIngredientCategory3 = ingredientCategoryService.saveCategory(ingredientCategory3);
		IngredientCategory ingredientCategory1 = IngredientCategory.builder().categoryName("Salsa de tomate").build();
		ingredientCategoryService.saveCategory(ingredientCategory1);
		IngredientCategory ingredientCategory2 = IngredientCategory.builder().categoryName("Micelaneos").build();
		ingredientCategoryService.saveCategory(ingredientCategory2);
		
		Ingredient sal = Ingredient.builder().ingredientName("sal").ingredientAmount(5000).build();
		Ingredient savedSal = ingredientService.saveIngredient(savedIngredientCategory3, sal);

		Ingredient pimienta = Ingredient.builder().ingredientName("pimienta").ingredientAmount(4000).build();
		Ingredient savedPimienta = ingredientService.saveIngredient(savedIngredientCategory3, pimienta);
		
	
		
		}
	
	@Test
	@Order(2)
	void saveIngredientMixTest() {
		Ingredient ingredient = Ingredient.builder().id(1L).ingredientName("sal").build();
		
		IngredientMix newMix = IngredientMix.builder().ingredient(ingredient).build();
		IngredientMix mix = ingredientMixService.saveIngredientMix(newMix, 1L);
		assertTrue(mix.getId()!=null);
		
		
	}
	
	@Test
	@Order(3)
	void whenTryToAddExistingIngredient_ExceptionMustThrow() {
		RuntimeException exception =  Assertions.assertThrows(ValueAlreadyExistException.class, ()->{ 
			Ingredient ingredient = Ingredient.builder().ingredientName("sal").build();
			
			IngredientMix newMix = IngredientMix.builder().ingredient(ingredient).build();
			IngredientMix mix = ingredientMixService.saveIngredientMix(newMix, 1L);
		
			
			
		
		}, "No Exception throw");
		
		assertTrue(exception.getMessage().equals( "Ingredient already exist in this recipe"));
	}
}
