package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.lord.arbam.dto.ProductDto;
import com.lord.arbam.mapper.ProductMapper;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.repository.ProductCategoryRepository;
import com.lord.arbam.repository.ProductPriceRepository;
import com.lord.arbam.repository.ProductStockRepository;
import com.lord.arbam.service_impl.ProductCategoryServiceImpl;
import com.lord.arbam.service_impl.ProductServiceImpl;

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
		productDto.setCategoryId(savedCategory.getId());
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

	

}
