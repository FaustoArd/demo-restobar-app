package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.lord.arbam.dtos.ProductDto;
import com.lord.arbam.mappers.ProductMapper;
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.repositories.ProductCategoryRepository;
import com.lord.arbam.repositories.ProductPriceRepository;
import com.lord.arbam.repositories.ProductStockRepository;
import com.lord.arbam.services_impl.ProductCategoryServiceImpl;
import com.lord.arbam.services_impl.ProductServiceImpl;

import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductServiceImpl productService;

	@InjectMocks
	private ProductCategoryServiceImpl categoryService;

	@Mock
	private ProductStockRepository productStockRepository;
	
	@Mock
	private ProductCategoryRepository categoryRepository;

	@Mock
	private ProductPriceRepository productPriceRepository;

	private Product product;
	
	private ProductCategory newCategory;
	
	

	@Test
	@Order(1)
	void setup() {
		ProductCategory  category = new ProductCategory();
		category.setCategoryName("Milanesas");
		
		ProductCategory savedCategory = categoryRepository.save(category);
		
		Calendar expTime = Calendar.getInstance();
		expTime.set(2023, 11,23);
		ProductDto productDto = new ProductDto();
		productDto.setProductName("Pizza Muzarella");
		productDto.setCategoryId(1L);
		productDto.setProductPrice(new BigDecimal(3000.00));
		productDto.setProductStock(50);
		product = new Product();
		product = ProductMapper.INSTANCE.toProduct(productDto);
		Product savedProduct = productService.saveProduct(product);
		ProductPrice savedPrice = productPriceRepository.findById(savedProduct.getProductPrice().getId()).get();
		ProductStock savedStock = productStockRepository.findById(savedProduct.getProductStock().getId()).get();
		//assertNotNull(savedProduct.getId());
		//assertEquals(savedProduct.getCategory().getId(), notNull());
		assertEquals(savedProduct.getProductPrice().getPrice(), savedPrice.getPrice());
		assertEquals(savedProduct.getProductStock().getProductStock(), savedStock.getProductStock());
	}

	@Test
	@Order(2)
	void createNewProduct() throws Exception {
	
	

	}

}
