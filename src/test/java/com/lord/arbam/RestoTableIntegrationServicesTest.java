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
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.lord.arbam.models.Employee;
import com.lord.arbam.models.Product;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableClosed;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.models.WorkingDay;
import com.lord.arbam.repositories.RestoTableRepository;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.ProductService;
import com.lord.arbam.services.RestoTableClosedService;
import com.lord.arbam.services.RestoTableOrderService;
import com.lord.arbam.services.RestoTableService;
import com.lord.arbam.services.WorkingDayService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = com.lord.arbam.ArbamApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestoTableIntegrationServicesTest {

	@Autowired
	private RestoTableService restoTableService;

	@Autowired
	private RestoTableOrderService restoTableOrderService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private WorkingDayService workingDayService;

	@Autowired
	private RestoTableClosedService restoTableClosedService;

	private Long workingDayId;

	@Test
	@Order(1)
	public void WorkingDayStartTest() {
		List<Employee> emps = new ArrayList<>();
		emps.add(new Employee(1L));
		emps.add(new Employee(3L));

		WorkingDay workingDay = WorkingDay.builder().cashierName("Mirta").totalStartCash(new BigDecimal(5000.00))
				.totalWaitressSalary(new BigDecimal(10000.00)).totalCashierSalary(new BigDecimal(4000.00))
				.waitresses(emps).build();
		WorkingDay startedWorkingDay = workingDayService.startWorkingDay(workingDay);
		this.workingDayId = startedWorkingDay.getId();
		assertTrue(startedWorkingDay.isDayStarted());
		assertEquals(startedWorkingDay.getCashierName(), "Mirta");
		assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(), 5000.00);
		assertEquals(startedWorkingDay.getTotalCashierSalary().doubleValue(), 4000.00);
		assertEquals(startedWorkingDay.getTotalWaitressSalary().doubleValue(), 10000.00);
	}

	// table id : 1
	@Test
	@Order(2)
	void whenOpenRestoTable1_mustReturnRestoTable() {
		Employee emp = employeeService.findEmployeeById(1L);
		RestoTable table = RestoTable.builder().id(1L).employee(emp).tableNumber(1).build();
		RestoTable returnedTable = restoTableService.openRestoTable(table);
		assertEquals(returnedTable.getId(), 1);
		assertEquals(returnedTable.getEmployee().getEmployeeName(), "Carla");
		assertTrue(returnedTable.isOpen());
		assertEquals(returnedTable.getTotalTablePrice().intValue(), 0);
	}

	/**
	 * One order per product is created,if user attempt to add a product already
	 * ordered,the existing product order will be updated
	 **/
	// table id : 1
	// Product id1: name="Grande Muzza",price=1500.00
	@Test
	@Order(3)
	void whenCreateNewOrderRestoTable1_MustReturnOrder() {
		Product product = Product.builder().id(1L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(2).build();
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		assertTrue(createdOrder.getId() != null);
		assertEquals(createdOrder.getProduct().getProductName(), "Grande Muzza");
		assertEquals(createdOrder.getTotalOrderPrice().doubleValue(), 3000.00);

	}

	// Product id2: name="Grande Cebolla", price=1800.00
	// table id : 1
	@Test
	@Order(4)
	void createNewOrderRestoTable1() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(1).build();
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		assertTrue(createdOrder.getId() != null);
		assertEquals(createdOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(createdOrder.getTotalOrderPrice().doubleValue(), 1800.00);

	}

	// table id : 1
	@Test
	@Order(5)
	void checkRestoTableOrdersRestoTable1() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		assertEquals(orders.get(0).getTotalOrderPrice().doubleValue(), 3000.00);
		assertEquals(orders.get(1).getTotalOrderPrice().doubleValue(), 1800.00);
	}

	// table id : 1
	@Test
	@Order(6)
	void whenUpdateRestoTablePriceRestoTable1_MustReturnRestotableUpdatedTotalPrice() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 4800.00);
	}

	// table id : 2
	@Test
	@Order(7)
	void whenOpenRestoTable8_mustReturnRestoTable() {
		Employee emp = employeeService.findEmployeeById(3L);
		RestoTable table = RestoTable.builder().id(2L).employee(emp).tableNumber(8).build();
		RestoTable returnedTable = restoTableService.openRestoTable(table);
		assertEquals(returnedTable.getId(), 2);
		assertEquals(returnedTable.getEmployee().getEmployeeName(), "Silvana");
		assertTrue(returnedTable.isOpen());
		assertEquals(returnedTable.getTotalTablePrice().intValue(), 0);
	}

	// table id : 2
	@Test
	@Order(8)
	void createNewOrderRestoTable8() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(2L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(3).build();
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		assertTrue(createdOrder.getId() != null);
		assertEquals(createdOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(createdOrder.getTotalOrderPrice().doubleValue(), 5400.00);

	}

	// table id : 2
	@Test
	@Order(9)
	void whenTryToCreateExistingRestoTable8Order_MustResturnUpdatedRestoTableOrder() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(2L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(5).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(2L);
		assertEquals(orders.size(), 1);
		assertEquals(updatedOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 14400.00);
	}
	// table id : 2
		@Test
		@Order(10)
		void whenUpdateRestoTablePriceRestoTable8_MustReturnRestotableUpdatedTotalPrice() {
			RestoTable table = restoTableService.findRestoTableById(2L);
			List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
			RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
			assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 14400.00);
		}
	

	// table id : 1
	@Test
	@Order(11)
	void whenTryToCreateExistingRestoTable1Order_MustResturnUpdatedRestoTableOrder() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(3).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		assertEquals(orders.size(), 2);
		assertEquals(updatedOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 7200.00);
	}

	// table id : 1
	@Test
	@Order(12)
	void checkForRestoTableTotalPriceRestoTable1() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 10200.00);
	}

	// table id : 1
	@Test
	@Order(13)
	void updateAgainOrderWithProductId2RestoTable1() {
		Product product = Product.builder().id(2L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(3).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		assertEquals(orders.size(), 2);
		assertEquals(updatedOrder.getProduct().getProductName(), "Grande Cebolla");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 12600.00);

	}

	// table id : 1
	@Test
	@Order(14)
	void checkAgainForRestoTableTotalPriceRestoTable1() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 15600.00);
	}

	// table id : 1
	@Test
	@Order(15)
	void whenDeleteOrderbyIdRestoTable1_RestoTableTotalPriceMustBeUpdated() {
		restoTableOrderService.deleteOderById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		RestoTable table = restoTableService.findRestoTableById(1L);
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(orders.size(), 1);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 12600.00);

	}

	// table id : 1
	// Product id3:"Cerveza heineken" price : 1700.00
	@Test
	@Order(16)
	void updateAgainOrderWithProductId3RestoTable1() {
		Product product = productService.findProductById(3L);
		RestoTable table = restoTableService.findRestoTableById(1L);
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(7).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(orders.size(), 2);
		assertEquals(updatedOrder.getProduct().getProductName(), "Cerveza heineken");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 11900.00);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 24500.00);
	}

	// table id : 1
	@Test
	@Order(17)
	public void closeRestoTable1Test() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		RestoTable closedTable = restoTableService.closeRestoTable(1L, workingDayId,"Efectivo");
		List<RestoTableClosed> restoTablesClosed = restoTableClosedService.findAllByWorkingDayId(workingDayId);

		assertEquals(table.getTotalTablePrice().doubleValue(), 24500.00);
		assertEquals(table.getEmployee().getId(), 1L);

		assertEquals(restoTablesClosed.stream().filter(rt -> rt.getTableNumber() == 1).findFirst().get().getTotalPrice()
				.doubleValue(), 24500.00);
		assertEquals(
				restoTablesClosed.stream().filter(rt -> rt.getTableNumber() == 1).findFirst().get().getEmployeeName(),
				"Carla");
		assertEquals(restoTablesClosed.stream().filter(rt -> rt.getTableNumber() == 1).findFirst().get().getWorkingDay()
				.getId(), 1);
		assertEquals(restoTablesClosed.stream().filter(rt -> rt.getTableNumber() == 1).findFirst().get().getPaymentMethod(), "Efectivo");

		assertEquals(closedTable.getId(), table.getId());
		assertEquals(closedTable.getEmployee(), null);
		assertEquals(closedTable.getPaymentMethod(), null);
		assertEquals(closedTable.getTableNumber(), null);
		assertEquals(closedTable.getTotalTablePrice(), null);

	}

	@Test
	@Order(18)
	void checkWorkingDayStatus() {
		boolean result = this.workingDayService.isWorkingDayStarted(workingDayId);
		assertTrue(result);
	}

	@Test
	@Order(19)
	void updateWorkingDay() {
		WorkingDay day = workingDayService.findWorkingDayById(workingDayId);
		List<Employee> ids = new ArrayList<>();
		ids.add(new Employee(2L));
		ids.add(new Employee(1L));
		day.setWaitresses(ids);
		day.setTotalDishWasherSalary(new BigDecimal(6600.00));
		day.setTotalChefSalary(new BigDecimal(7000.00));
		day.setTotalHelperSalary(new BigDecimal(3500.00));
		day.setTotalStartCash(new BigDecimal(10000.00));
		WorkingDay updatedDay = workingDayService.updateWorkingDay(day);
		assertEquals(updatedDay.getId(), workingDayId);
		assertEquals(day.getWaitresses().size(), 2);
		assertTrue(day.getWaitresses().stream().filter(wt -> wt.getId() == 2L).findFirst().isPresent());
		assertTrue(day.getWaitresses().stream().filter(wt -> wt.getId() == 1L).findFirst().isPresent());
		assertEquals(updatedDay.getTotalChefSalary().doubleValue(), 7000.00);
		assertEquals(day.getTotalHelperSalary().doubleValue(), 3500.00);
		assertEquals(updatedDay.getTotalStartCash().doubleValue(), 10000.00);

	}
	
	@Test
	@Order(20)
	void closeRestoTable8Test() {
		RestoTable table = restoTableService.findRestoTableById(2L);
		RestoTable closedTable = restoTableService.closeRestoTable(2L, workingDayId,"Tajeta de debito");
		List<RestoTableClosed> tablesClosed = restoTableClosedService.findAllByWorkingDayId(workingDayId);
		
		assertEquals(tablesClosed.stream().filter(rt -> rt.getTableNumber() == 8).findFirst().get().getTotalPrice()
				.doubleValue(), 14400.00);
		assertEquals(
				tablesClosed.stream().filter(rt -> rt.getTableNumber() == 8).findFirst().get().getEmployeeName(),
				"Silvana");
		assertEquals(tablesClosed.stream().filter(rt -> rt.getTableNumber() == 8).findFirst().get().getWorkingDay()
				.getId(), 1);
		assertEquals(tablesClosed.stream().filter(rt -> rt.getTableNumber() == 8).findFirst().get().getPaymentMethod(),"Tajeta de debito");

		assertEquals(closedTable.getId(), table.getId());
		assertEquals(closedTable.getEmployee(), null);
		assertEquals(closedTable.getPaymentMethod(), null);
		assertEquals(closedTable.getTableNumber(), null);
		assertEquals(closedTable.getTotalTablePrice(), null);
		assertEquals(closedTable.getPaymentMethod(),null);
	}

	//total Table N1 = 24500.00
	//total Table N8 = 14400.00
	@Test
	@Order(21)
	void closeWorkingDay() {
		WorkingDay day = workingDayService.closeWorkingDay(workingDayId);
		assertEquals(day.getId(), workingDayId);
		assertEquals(day.getTotalCashierSalary().doubleValue(), 4000.00);
		assertEquals(day.getTotalWaitressSalary().doubleValue(), 10000.00);
		assertEquals(day.getTotalChefSalary().doubleValue(), 7000.00);
		assertEquals(day.getTotalDishWasherSalary().doubleValue(), 6600.00);
		assertEquals(day.getTotalHelperSalary().doubleValue(), 3500);
		assertEquals(day.getTotalCash().doubleValue(),  38900.00);
		assertEquals(day.getTotalCashDiscounted().doubleValue(), 7800.00);
	}
	

}
