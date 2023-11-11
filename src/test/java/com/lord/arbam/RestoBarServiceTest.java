package com.lord.arbam;

import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.lord.arbam.models.Employee;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.ProductService;
import com.lord.arbam.services.RestoTableOrderService;
import com.lord.arbam.services.RestoTableService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class RestoBarServiceTest {
	
	@Autowired
	private RestoTableService restoTableService;

	@Autowired
	private RestoTableOrderService restoTableOrderService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProductService productService;
	
	
	@Test
	@Order(1)
	void whenCreateRestoTable_mustReturnRestoTable() {
		Employee emp = employeeService.findEmployeeById(1L);
		RestoTable table = RestoTable.builder().id(1L).employee(emp).tableNumber(1).build();
		RestoTable returnedTable = restoTableService.createRestoTable(table);
		assertEquals(returnedTable.getId(),1);
		assertEquals(returnedTable.getEmployee().getEmployeeName(),"Carla");
		assertTrue(returnedTable.isOpen());
	}
	
	//Product id1: name="Grande Muzza",price=1500.00
	//Product id2: name="Grande Cebolla", price=1800.00
	@Test
	@Order(2)
	void whenCreateNewOrderMustReturnOrder() {
	
		}
	
}
