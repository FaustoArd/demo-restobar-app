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
import java.util.List;

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
public class RestoTableIntegrationServicesTest {
	
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
	void whenOpenRestoTable_mustReturnRestoTable() {
		Employee emp = employeeService.findEmployeeById(1L);
		RestoTable table = RestoTable.builder().id(1L).employee(emp).tableNumber(1).build();
		RestoTable returnedTable = restoTableService.openRestoTable(table);
		assertEquals(returnedTable.getId(),1);
		assertEquals(returnedTable.getEmployee().getEmployeeName(),"Carla");
		assertTrue(returnedTable.isOpen());
		assertEquals(returnedTable.getTotalTablePrice().intValue(), 0);
	}
	
	
	/**One order per product is created,if user attemp to add a product already ordered,the existing order will be updated **/
	//Product id1: name="Grande Muzza",price=1500.00
	//Product id2: name="Grande Cebolla", price=1800.00
	@Test
	@Order(2)
	void whenCreateNewOrder_MustReturnOrder() {
		Product product = Product.builder().id(1L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(2).build();
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		assertTrue(createdOrder.getId()!=null);
		assertEquals(createdOrder.getProduct().getProductName(), "Grande Muzza");
		assertEquals(createdOrder.getTotalOrderPrice().doubleValue(), 3000.00);
	
		}
	@Test
	@Order(3)
	void createNewOrder() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(1).build();
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		assertTrue(createdOrder.getId()!=null);
		assertEquals(createdOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(createdOrder.getTotalOrderPrice().doubleValue(), 1800.00);
		
	}
	@Test
	@Order(4)
	void checkRestoTableOrders() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		assertEquals(orders.get(0).getTotalOrderPrice().doubleValue(), 3000.00);
		assertEquals(orders.get(1).getTotalOrderPrice().doubleValue(), 1800.00);
	}
	
	@Test
	@Order(5)
	void whenUpdateRestoTablePrice_MustReturnRestotable() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 4800.00);
	}
	@Test
	@Order(6)
	void whenTryToCreateExistingRestoTableOrder_MustResturnUpdatedRestoTableOrder() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(3).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		assertEquals(orders.size(), 2);
		assertEquals(updatedOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 7200.00);
	}
	@Test
	@Order(7)
	void checkForRestoTableTotalPrice() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 10200.00);
	}
	@Test
	@Order(8)
	void updateAgainOrderWithProductId2L() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(3).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		assertEquals(orders.size(), 2);
		assertEquals(updatedOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 12600.00);
		
	}
	
}
