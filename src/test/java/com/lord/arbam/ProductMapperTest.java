package com.lord.arbam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Calendar;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.lord.arbam.dtos.ProductDto;
import com.lord.arbam.mappers.ProductMapper;
import com.lord.arbam.models.Product;



@SpringBootTest
public class ProductMapperTest {
	
	
	
	@Test
	void ProductDtoToProductTest() {
	
		Calendar expTime = Calendar.getInstance();
		expTime.set(2023, 11,28);
		ProductDto productDto = new ProductDto();
		productDto.setProductName("Milanesa de pollo");
		productDto.setCategoryId(2L);
		productDto.setProductStock(20);
		productDto.setProductPrice(2000.00);
	
		
		Product product = ProductMapper.INSTANCE.toProduct(productDto);
		assertEquals(product.getCategory().getId(), 2L);
		assertEquals(product.getProductName(), "Milanesa de pollo");
		assertEquals(product.getProductPrice().getPrice(), 2000.00);
		assertEquals(product.getProductStock().getProductStock(), 20);
		
	}

}
