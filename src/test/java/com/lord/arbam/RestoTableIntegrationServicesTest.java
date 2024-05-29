package com.lord.arbam;

import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hamcrest.number.IsCloseTo;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.lord.arbam.dto.OrderPaymentMethodDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.OrderPaymentMethod;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.repository.EmployeeJobRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.EmployeeService;
import com.lord.arbam.service.IngredientMixService;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductService;
import com.lord.arbam.service.RestoTableClosedService;
import com.lord.arbam.service.RestoTableOrderService;
import com.lord.arbam.service.RestoTableService;
import com.lord.arbam.service.WorkingDayService;

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
	private RestoTableRepository restoTableRepository;
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private CategoryService<ProductCategory> productCategoryService;
	
	@Autowired
	private CategoryService<IngredientCategory> ingredientCategoryService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private IngredientMixService ingredientMixService;
	
	@Autowired
	private EmployeeJobRepository employeeJobRepository;
	
	@Autowired
	private RestoTableClosedService restoTableClosedService;

	private Long workingDayId;

	
	@Test
	@Order(1)
	void setup() {
		 PaymentMethod p1 = PaymentMethod.builder().paymentMethod("Efectivo").build();
		 PaymentMethod p2 = PaymentMethod.builder().paymentMethod("Tarjeta de debito").build();
		 PaymentMethod p3 = PaymentMethod.builder().paymentMethod("Transferencia").build();
		 PaymentMethod p4 = PaymentMethod.builder().paymentMethod("Mercado pago").build();
		 PaymentMethod p5 = PaymentMethod.builder().paymentMethod("Tarjeta de credito").build();
		 List<PaymentMethod> payments = new ArrayList<>();
		 payments.add(p1);
		 payments.add(p2);
		 payments.add(p3);
		 payments.add(p4);
		 payments.add(p5);
		 paymentMethodRepository.saveAll(payments);

		ProductCategory pCategory1 = ProductCategory.builder().categoryName("Pizza").build();
		productCategoryService.saveCategory(pCategory1);
		ProductCategory pCategory2 = ProductCategory.builder().categoryName("Pollo").build();
		productCategoryService.saveCategory(pCategory2);
		
		ProductCategory pCategory3 = ProductCategory.builder().categoryName("Cerveza").build();
		productCategoryService.saveCategory(pCategory3);

	
		Product product1 = Product.builder().productName("Grande Muzza").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(1500.00))).build();
		Product savedProduct1 = productService.saveProduct(product1);

		Product product2 = Product.builder().productName("Grande Cebolla").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(1800.00))).build();
		Product savedProduct2 = productService.saveProduct(product2);
		
		Product product3 = Product.builder().productName("Cerveza heineken").category(pCategory3)
				.productPrice(new ProductPrice(new BigDecimal(1700.00))).build();
		Product savedProduct3 = productService.saveProduct(product3);
		
		Product product4 = Product.builder().productName("Cerveza Brahma").category(pCategory3)
				.productPrice(new ProductPrice(new BigDecimal(2500.00))).build();
		Product savedProduct4 = productService.saveProduct(product4);
		
		Product product5 = Product.builder().productName("Pollo a la cartunia").category(pCategory2)
				.productPrice(new ProductPrice(new BigDecimal(2800.00))).build();
		Product savedProduct5 = productService.saveProduct(product5);
		
		Product product6 = Product.builder().productName("Pollo al horno").category(pCategory2)
				.productPrice(new ProductPrice(new BigDecimal(2400.00))).build();
		Product savedProduct6 = productService.saveProduct(product6);

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
		
		IngredientMix mix1 = IngredientMix.builder().ingredient(savedSal).product(savedProduct1).ingredientAmount(500).build();
		ingredientMixService.saveIngredientMix(mix1,savedProduct1.getId());
		IngredientMix mix2 = IngredientMix.builder().ingredient(savedPimienta).product(savedProduct1).ingredientAmount(300).build();
		ingredientMixService.saveIngredientMix(mix2,savedProduct1.getId());
		
		Product product = productService.findProductById(1L);
		ProductStock stock = new ProductStock(10);
		Product savedProductstocked1 = productService.createProductStock(product, stock);
		
		Product findedProduct2 = productService.findProductById(2L);
		ProductStock stock2 = new ProductStock(20);
		Product savedProductstocked2 = productService.createProductStock(findedProduct2, stock2);
		
		Product findedProduct3 = productService.findProductById(3L);
		ProductStock stock3 = new ProductStock(30);
		Product savedProductstocked3 = productService.createProductStock(findedProduct3, stock3) ;
		
		EmployeeJob meseroJob = EmployeeJob.builder().jobRole("Mesera").employeeSalary(new BigDecimal(6000)).build();
		EmployeeJob savedMeseroJob = employeeJobRepository.save(meseroJob); 
		
		EmployeeJob cajeroJob = EmployeeJob.builder().jobRole("Cajera").employeeSalary(new BigDecimal(5000)).build(); 
		EmployeeJob savedCajeroJob = employeeJobRepository.save(cajeroJob); 
		
		EmployeeJob bacheroJob = EmployeeJob.builder().jobRole("Bachero").employeeSalary(new BigDecimal(4000)).build(); 
		EmployeeJob savedBacheroJob = employeeJobRepository.save(bacheroJob); 
		
		EmployeeJob cocineroJob = EmployeeJob.builder().jobRole("Cocinero").employeeSalary(new BigDecimal(6500)).build(); 
		EmployeeJob savedCocineroJob = employeeJobRepository.save(cocineroJob);
		
		Employee emp1 = Employee.builder().employeeName("Carla Mesera").employeeJob(savedMeseroJob).build();
		Employee savedEmp1 =  employeeService.saveEmployee(emp1);
		
		Employee emp2 = Employee.builder().employeeName("Marina Mesera").employeeJob(savedMeseroJob).build();
		Employee savedEmp2 =  employeeService.saveEmployee(emp2);
		
		Employee emp3 = Employee.builder().employeeName("Silvana Mesera").employeeJob(savedMeseroJob).build();
		Employee savedEmp3 =  employeeService.saveEmployee(emp3);
		
		Employee emp4 = Employee.builder().employeeName("Mirta Cajera").employeeJob(savedCajeroJob).build();
		Employee savedEmp4 =  employeeService.saveEmployee(emp4);
		
		Employee emp5 = Employee.builder().employeeName("Susana Bachera").employeeJob(savedBacheroJob).build();
		Employee savedEmp5 =  employeeService.saveEmployee(emp5);
		
		ArrayList<RestoTable> tables = new ArrayList<RestoTable>();
		for(int i = 1; i<31;i++) {
			RestoTable table = new RestoTable();
			tables.add(table);
			
		}
		restoTableRepository.saveAll(tables);
	}
	@Test
	@Order(2)
	void WorkingDayStart() {
		List<Employee> emps = new ArrayList<>();
		emps.add(new Employee(1L));
		emps.add(new Employee(3L));
		emps.add(new Employee(5L));

		WorkingDay workingDay = WorkingDay.builder().totalStartCash(new BigDecimal(5000.00))
				.employees(emps).build();
		WorkingDay startedWorkingDay = workingDayService.startWorkingDay(workingDay);
		this.workingDayId = startedWorkingDay.getId();
		assertTrue(startedWorkingDay.isDayStarted());
		assertEquals(startedWorkingDay.getTotalStartCash().doubleValue(), 5000.00);
		
	}

	// table id : 1
	//table number: 1
	@Test
	@Order(3)
	void openRestoTable() {
		Employee emp = employeeService.findEmployeeById(1L);
		RestoTable table = RestoTable.builder().id(1L).employee(emp).tableNumber(1).build();
		RestoTable returnedTable = restoTableService.openRestoTable(table);
		assertEquals(returnedTable.getId(), 1);
		assertEquals(returnedTable.getEmployee().getEmployeeName(), "Carla Mesera");
		assertTrue(returnedTable.isOpen());
		assertEquals(returnedTable.getTotalTablePrice().intValue(), 0);
	}

	/**
	 * One order per product is created,if user attempt to add a product already
	 * ordered,the existing product order will be updated
	 **/
	// table id : 1
	//table number: 1
	// Product id1: name="Grande Muzza",price=1500.00
	@Test
	@Order(4)
	void whenCreateNewOrderInRestoTable_MustReturnOrder() {
		Product product = Product.builder().id(1L).build();
		RestoTable table = RestoTable.builder().id(1L).build();
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(2).build();
		RestoTableOrder createdOrder = restoTableOrderService.createOrder(order);
		assertTrue(createdOrder.getId() != null);
		assertEquals(createdOrder.getProduct().getProductName(), "Grande Muzza");
		assertEquals(createdOrder.getTotalOrderPrice().doubleValue(), 3000.00);

	}

	// table id : 1
		//table number: 1
	// Product id2: name="Grande Cebolla", price=1800.00
	@Test
	@Order(5)
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
	@Order(6)
	void checkRestoTableOrdersRestoTable1() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		assertEquals(orders.get(0).getTotalOrderPrice().doubleValue(), 3000.00);
		assertEquals(orders.get(1).getTotalOrderPrice().doubleValue(), 1800.00);
	}

	// table id : 1
	@Test
	@Order(7)
	void whenUpdateRestoTableTotalPrice_MustReturnUpdatedTotalPrice() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 4800.00);
	}

	// table id : 2
	// table number: 8
	@Test
	@Order(8)
	void whenOpenRestoTable8_mustReturnRestoTable() {
		Employee emp = employeeService.findEmployeeById(3L);
		RestoTable table = RestoTable.builder().id(2L).employee(emp).tableNumber(8).build();
		RestoTable returnedTable = restoTableService.openRestoTable(table);
		assertEquals(returnedTable.getId(), 2);
		assertEquals(returnedTable.getEmployee().getEmployeeName(), "Silvana Mesera");
		assertTrue(returnedTable.isOpen());
		assertEquals(returnedTable.getTotalTablePrice().intValue(), 0);
	}

	// table id : 2
	// table number: 8
	// Product id2: name="Grande Cebolla", price=1800.00
	@Test
	@Order(9)
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
	// table number: 8
	
	@Test
	@Order(10)
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
	// table number: 8
		@Test
		@Order(11)
		void whenUpdateRestoTablePriceRestoTable8_MustReturnRestotableUpdatedTotalPrice() {
			RestoTable table = restoTableService.findRestoTableById(2L);
			List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
			RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
			assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 14400.00);
		}
	

	// table id : 1
		// table number: 1	
	@Test
	@Order(12)
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
	// table number: 1	
	@Test
	@Order(13)
	void checkForRestoTableTotalPriceRestoTable1() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 10200.00);
	}

	// table id : 1
	// table number: 1	
	@Test
	@Order(14)
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
	// table number: 1	
	@Test
	@Order(15)
	void checkAgainForRestoTableTotalPriceRestoTable1() {
		RestoTable table = restoTableService.findRestoTableById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(table.getId());
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 15600.00);
	}

	// table id : 1
	// table number: 1	
	@Test
	@Order(16)
	void whenDeleteOrderbyIdRestoTable1_RestoTableTotalPriceMustBeUpdated() {
		restoTableOrderService.deleteOderById(1L);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		RestoTable table = restoTableService.findRestoTableById(1L);
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(orders.size(), 2);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 14100.00);

	}

	// table id : 1
	// table number: 1	
	// Product id3:"Cerveza heineken" price : 1700.00
	@Test
	@Order(17)
	void updateAgainOrderWithProductId3RestoTable1() {
		Product product = productService.findProductById(3L);
		RestoTable table = restoTableService.findRestoTableById(1L);
		RestoTableOrder order = RestoTableOrder.builder().product(product).restoTable(table).productQuantity(7).build();
		RestoTableOrder updatedOrder = restoTableOrderService.createOrder(order);
		List<RestoTableOrder> orders = restoTableOrderService.findAllByRestoTableId(1L);
		RestoTable updatedTable = restoTableService.updateRestoTableTotalPrice(table, orders);
		assertEquals(orders.size(), 3);
		assertEquals(updatedOrder.getProduct().getProductName(), "Cerveza heineken");
		assertEquals(updatedOrder.getTotalOrderPrice().doubleValue(), 11900.00);
		assertEquals(updatedTable.getTotalTablePrice().doubleValue(), 26000.00);
	}

	
	

}
