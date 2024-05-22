package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.lord.arbam.exception.NegativeNumberException;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.IngredientMixService;
import com.lord.arbam.service.ProductService;
import com.lord.arbam.service.ProductStockService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductUpdateStockTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private CategoryService<ProductCategory> productCategoryService;

	@Autowired
	private CategoryService<IngredientCategory> ingredientCategoryService;

	@Autowired
	private IngredientMixService ingredientMixService;

	@Autowired
	private IngredientService ingredientService;

	
	private Long currentStockId;
	





	@Test
	@Order(1)
	void setup() {
		ProductCategory pCategory1 = ProductCategory.builder().categoryName("Pizza").build();
		productCategoryService.saveCategory(pCategory1);
		ProductCategory pCategory2 = ProductCategory.builder().categoryName("Pollo").build();
		productCategoryService.saveCategory(pCategory2);

		Product grandeMuza = Product.builder().productName("Grande Muza").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(400.00))).build();
		Product savedGrandeMuza = productService.saveProduct(grandeMuza);

		Product grandeCebolla = Product.builder().productName("Grande Cebolla").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(400.00))).build();
		Product savedGrandeCebolla = productService.saveProduct(grandeCebolla);

		IngredientCategory salsaDeTomate = IngredientCategory.builder().categoryName("Salsa de tomate").build();
		ingredientCategoryService.saveCategory(salsaDeTomate);

		IngredientCategory verduras = IngredientCategory.builder().categoryName("Micelaneos").build();
		ingredientCategoryService.saveCategory(verduras);

		IngredientCategory especias = IngredientCategory.builder().categoryName("Especias").build();
		IngredientCategory savedEspecias = ingredientCategoryService.saveCategory(especias);

		Ingredient sal = Ingredient.builder().ingredientName("sal").ingredientAmount(4000).build();
		Ingredient savedSal = ingredientService.saveIngredient(savedEspecias, sal);

		Ingredient pimienta = Ingredient.builder().ingredientName("pimienta").ingredientAmount(8000).build();
		Ingredient savedPimienta = ingredientService.saveIngredient(savedEspecias, pimienta);

		IngredientMix mix1 = IngredientMix.builder().ingredient(savedSal).product(savedGrandeMuza).ingredientAmount(500)
				.build();
		ingredientMixService.saveIngredientMix(mix1,savedGrandeMuza.getId());
		IngredientMix mix2 = IngredientMix.builder().ingredient(savedPimienta).product(savedGrandeMuza)
				.ingredientAmount(300).build();
		ingredientMixService.saveIngredientMix(mix2,savedGrandeMuza.getId());
	}

	@Test
	@Order(2)
	void whenSaveProductStock_MustCreateProductStock_UpdateIngredientsAmount_AndReturnProduct() {
		Product product = productService.findProductById(1L);
		ProductStock stock = new ProductStock(5);
		
		
		if(product.isMixed()) {
			ingredientService.decreaseIngredientAmount(stock.getProductStock(), product.getId());
		}
		ProductStock savedStock = productStockService.updateStock(stock, product.getId());
		this.currentStockId = savedStock.getId();
		Product savedProduct = productService.createProductStock(product, savedStock);
		Ingredient findedSal = ingredientService.findIngredientById(1L);
		Ingredient findedPimienta = ingredientService.findIngredientById(2L);
		ProductStock findedStock = productStockService.findStockByProductId(savedProduct.getId());
		assertTrue(savedProduct.getId() != null);
		assertEquals(savedProduct.getProductStock().getProductStock(), 5);
		assertTrue(findedStock.getId() != null);
		assertEquals(findedSal.getIngredientName(), "sal");
		assertEquals(findedSal.getIngredientAmount(), 1500);
		assertEquals(findedPimienta.getIngredientName(), "pimienta");
		assertEquals(findedPimienta.getIngredientAmount(), 6500);
		assertEquals(findedStock.getProductStock(), 5);
	}

	@Test
	@Order(3)
	void checkForMixedBoolean() {
		Product trueProduct = productService.findProductById(1L);
		Product falseProduct = productService.findProductById(2L);
		assertTrue(trueProduct.isMixed());
		assertEquals(trueProduct.getProductPrice().getPrice().doubleValue(),400.00);
		assertFalse(falseProduct.isMixed());
	}

	@Test
	@Order(4)
	void udpateStockTest() {
		RuntimeException exception = Assertions.assertThrows(NegativeNumberException.class, ()->{ 
			
			Product product = productService.findProductById(1L);
			ProductStock newStock = new ProductStock(10);
			if(product.isMixed()) {
			ingredientService.decreaseIngredientAmount(newStock.getProductStock(), product.getId());
			}
			ProductStock stock = productStockService.findStockById(this.currentStockId);
			ProductStock savedStock = productStockService.updateStock(newStock, 1L);
			Product savedProduct = productService.createProductStock(product, savedStock);
		}, "No Exception throw");
		
		assertTrue(exception.getMessage().equals( "Not enough ingredient amount to produce that stock"));
	
		
	}
	@Test
	@Order(5)
	void whenCheckStockAndIngredients_StockQuantity_AndIngredientAmount_MustBeSameAsMethodTwo(){
		ProductStock stock = productStockService.findStockById(currentStockId);
		Ingredient findedSal = ingredientService.findIngredientById(1L);
		Ingredient findedPimienta = ingredientService.findIngredientById(2L);
		assertEquals(findedSal.getIngredientName(), "sal");
		assertEquals(findedSal.getIngredientAmount(), 1500);
		assertEquals(findedPimienta.getIngredientName(), "pimienta");
		assertEquals(findedPimienta.getIngredientAmount(), 6500);
		assertEquals(stock.getProductStock(), 5);
		
	}
	@Test
	@Order(6)
	void whenDeleteStock_IngredientsMustBeUpdated() {
		
		Product product = productService.findProductById(1L);
		ProductStock stock = new ProductStock(3);
		ProductStock deleteStock = productStockService.reduceStock(stock, product.getId());
		if(product.isMixed()) {
			ingredientService.increaseIngredientAmount(stock.getProductStock(), product.getId());
		}
		Ingredient findedSal = ingredientService.findIngredientById(1L);
		Ingredient findedPimienta = ingredientService.findIngredientById(2L);
		assertEquals(findedSal.getIngredientName(),"sal" );
		assertEquals(findedSal.getIngredientAmount(), 3000);
		assertEquals(findedPimienta.getIngredientName(), "pimienta");
		assertEquals(findedPimienta.getIngredientAmount(), 7400);
		assertEquals(deleteStock.getProductStock(), 2);
		
	}
	@Test
	@Order(7)
	void whenDeleteStockProduceNegativeStockQuantity_ExceptionMustBeThrown() {
		
			RuntimeException exception = Assertions.assertThrows(NegativeNumberException.class, ()->{ 
				
				Product product = productService.findProductById(1L);
				ProductStock stockReduced = new ProductStock(10);
				ProductStock deleteStock = productStockService.reduceStock(stockReduced, product.getId());
				if(product.isMixed()) {
				ingredientService.increaseIngredientAmount(stockReduced.getProductStock(), product.getId());
				}
				
			
			}, "No Exception throw");
			
			assertTrue(exception.getMessage().equals( "Stock con numero negativo o el resultado de la reduccion de stock daba un numero negativo"));
		
			
		}
	@Test
	@Order(8)
	void whenCheckStockAndIngredients_StockQuantity_AndIngredientAmount_MustBeSameAsMethodSeven(){
		ProductStock stock = productStockService.findStockById(currentStockId);
		Ingredient findedSal = ingredientService.findIngredientById(1L);
		Ingredient findedPimienta = ingredientService.findIngredientById(2L);
		assertEquals(findedSal.getIngredientName(), "sal");
		assertEquals(findedSal.getIngredientAmount(), 3000);
		assertEquals(findedPimienta.getIngredientName(), "pimienta");
		assertEquals(findedPimienta.getIngredientAmount(), 7400);
		assertEquals(stock.getProductStock(), 2);
		
	}
	
	}


