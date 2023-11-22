package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.models.ProductMix;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.services.IngredientService;
import com.lord.arbam.services.CategoryService;
import com.lord.arbam.services.ProductMixService;
import com.lord.arbam.services.ProductService;
import com.lord.arbam.services.ProductStockService;

@SpringBootTest
@AutoConfigureMockMvc
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
	private ProductMixService productMixService;
	
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
		
		Ingredient sal = Ingredient.builder().ingredientName("sal").ingredientAmount(5000).build();
		Ingredient savedSal = ingredientService.saveIngredient(savedEspecias, sal);
		
		Ingredient pimienta = Ingredient.builder().ingredientName("pimienta").ingredientAmount(4000).build();
		Ingredient savedPimienta = ingredientService.saveIngredient(savedEspecias, pimienta);
		
		ProductMix mix1 = ProductMix.builder().ingredient(savedSal).product(savedGrandeMuza).ingredientAmount(500).build();
		productMixService.saveProductMix(mix1);
		ProductMix mix2 = ProductMix.builder().ingredient(savedPimienta).product(savedGrandeMuza).ingredientAmount(300).build();
		productMixService.saveProductMix(mix2);
	}
	
	@Test
	@Order(2)
	void whenSaveProductStock_MustCreateOrUpdateProductStock_UpdateIngredientsAmount_AndReturnProduct() {
		Product product = productService.findProductById(1L);
		ProductStock stock = new ProductStock(10);
		Product savedProduct = productService.createProductStock(product, stock);
		Ingredient findedSal = ingredientService.findIngredientById(1L);
		Ingredient findedPimienta = ingredientService.findIngredientById(2L);
		ProductStock findedStock = productStockService.findStockByProductId(savedProduct.getId());
		this.currentStockId = findedStock.getId();
		assertTrue(savedProduct.getId()!=null);
		assertEquals(savedProduct.getProductStock().getProductStock(), 10);
		assertTrue(findedStock.getId()!=null);
		assertEquals(findedStock.getProductStock(), 10);
		assertEquals(findedSal.getIngredientName(), "sal");
		assertEquals(findedSal.getIngredientAmount(), 0);
		assertEquals(findedPimienta.getIngredientName(), "pimienta");
		assertEquals(findedPimienta.getIngredientAmount(),1000);
	}
	@Test
	@Order(3)
	void checkForMixedBoolean() {
		Product trueProduct = productService.findProductById(1L);
		Product falseProduct = productService.findProductById(2L);
		
		assertTrue(trueProduct.isMixed());
		assertEquals(trueProduct.getProductPrice().getPrice().doubleValue(), 400.00);
		assertFalse(falseProduct.isMixed());
	}
	@Test
	@Order(4)
	void udpateStockTest() {
		Product product = productService.findProductById(1L);
		ProductStock stock = productStockService.findStockById(this.currentStockId);
		Product savedProduct = productService.createProductStock(product, stock);
		Ingredient findedSal = ingredientService.findIngredientById(1L);
		Ingredient findedPimienta = ingredientService.findIngredientById(2L);
		ProductStock findedStock = productStockService.findStockByProductId(savedProduct.getId());
		assertTrue(savedProduct.getId()!=null);
		assertEquals(savedProduct.getProductStock().getProductStock(), 10);
		assertTrue(findedStock.getId()!=null);
		assertEquals(findedStock.getProductStock(), 10);
		assertEquals(findedSal.getIngredientName(), "sal");
		assertEquals(findedSal.getIngredientAmount(), 0);
		assertEquals(findedPimienta.getIngredientName(), "pimienta");
		assertEquals(findedPimienta.getIngredientAmount(),1000);
	}
}
