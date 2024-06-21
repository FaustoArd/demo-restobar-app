package com.lord.arbam;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lord.arbam.custom_mapper.RestoTableServiceCustomMapper;
import com.lord.arbam.dto.OrderPaymentMethodDto;
import com.lord.arbam.dto.PaymentMethodDto;
import com.lord.arbam.dto.RestoTableClosedDto;
import com.lord.arbam.dto.RestoTableOrderDto;
import com.lord.arbam.dto.WorkingDayDto;
import com.lord.arbam.mapper.RestoTableClosedMapper;
import com.lord.arbam.mapper.RestoTableOrderMapper;
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
import com.lord.arbam.repository.EmployeeJobRepository;
import com.lord.arbam.repository.OrderPaymentMethodRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.ProductCategoryRepository;
import com.lord.arbam.repository.RestoTableOrderRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.EmployeeService;
import com.lord.arbam.service.IngredientMixService;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductPriceService;
import com.lord.arbam.service.ProductService;
import com.lord.arbam.service.RestoTableClosedService;
import com.nimbusds.jose.shaded.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestoTableIntegrationControllersTest {

	@Autowired
	private RestoTableRepository restoTableRepository;

	@Autowired
	private RestoTableOrderRepository restoTableOrderRepository;

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
	private EmployeeService employeeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private RestoTableClosedService restoTableClosedService;

	@Autowired
	private OrderPaymentMethodRepository orderPaymentMethodRepository;

	@Autowired
	private RestoTableServiceCustomMapper restoTableServiceCustomMapper;

	private static final Gson gson = new Gson();

	@Autowired
	private MockMvc mockMvc;

	private MvcResult mvcResult;

	private String jwtToken;

	private Product mainProduct1;

	private Product mainProduct2;

	private Product mainProduct3;

	private Product mainProduct4;

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
		mainProduct1 = productService.saveProduct(product1);
		ProductPrice productPrice1 = productPriceService.findByProductId(mainProduct1.getId());
		mainProduct1.setProductPrice(productPrice1);

		Product product2 = Product.builder().productName("Grande Cebolla").category(pCategory1)
				.productPrice(new ProductPrice(new BigDecimal(1800.00))).build();
		mainProduct2 = productService.saveProduct(product2);
		ProductPrice productPrice2 = productPriceService.findByProductId(mainProduct2.getId());
		mainProduct2.setProductPrice(productPrice2);

		Product product3 = Product.builder().productName("Cerveza heineken").category(pCategory3)
				.productPrice(new ProductPrice(new BigDecimal(1700.00))).build();
		mainProduct3 = productService.saveProduct(product3);
		ProductPrice productPrice3 = productPriceService.findByProductId(mainProduct3.getId());
		mainProduct3.setProductPrice(productPrice3);

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

		IngredientMix mix1 = IngredientMix.builder().ingredient(savedSal).product(mainProduct1).ingredientAmount(500)
				.build();
		ingredientMixService.saveIngredientMix(mix1, mainProduct1.getId());
		IngredientMix mix2 = IngredientMix.builder().ingredient(savedPimienta).product(mainProduct1)
				.ingredientAmount(300).build();
		ingredientMixService.saveIngredientMix(mix2, mainProduct1.getId());

		Product product = productService.findProductById(1L);
		ProductStock stock = new ProductStock(10);
		Product savedProductstocked1 = productService.createProductStock(product, stock);

		Product findedProduct2 = productService.findProductById(2L);
		ProductStock stock2 = new ProductStock(20);
		Product savedProductstocked2 = productService.createProductStock(findedProduct2, stock2);

		Product findedProduct3 = productService.findProductById(3L);
		ProductStock stock3 = new ProductStock(30);
		Product savedProductstocked3 = productService.createProductStock(findedProduct3, stock3);

		EmployeeJob meseroJob = EmployeeJob.builder().jobRole("Mesera").employeeSalary(new BigDecimal(6000)).build();
		EmployeeJob savedMeseroJob = employeeJobRepository.save(meseroJob);

		EmployeeJob cajeroJob = EmployeeJob.builder().jobRole("Cajera").employeeSalary(new BigDecimal(5000)).build();
		EmployeeJob savedCajeroJob = employeeJobRepository.save(cajeroJob);

		EmployeeJob bacheroJob = EmployeeJob.builder().jobRole("Bachero").employeeSalary(new BigDecimal(4000)).build();
		EmployeeJob savedBacheroJob = employeeJobRepository.save(bacheroJob);

		EmployeeJob cocineroJob = EmployeeJob.builder().jobRole("Cocinero").employeeSalary(new BigDecimal(6500))
				.build();
		EmployeeJob savedCocineroJob = employeeJobRepository.save(cocineroJob);

		Employee emp1 = Employee.builder().employeeName("Carla Mesera").employeeJob(savedMeseroJob).build();
		Employee savedEmp1 = employeeService.saveEmployee(emp1);

		Employee emp2 = Employee.builder().employeeName("Marina Mesera").employeeJob(savedMeseroJob).build();
		Employee savedEmp2 = employeeService.saveEmployee(emp2);

		Employee emp3 = Employee.builder().employeeName("Silvana Mesera").employeeJob(savedMeseroJob).build();
		Employee savedEmp3 = employeeService.saveEmployee(emp3);

		Employee emp4 = Employee.builder().employeeName("Mirta Cajera").employeeJob(savedCajeroJob).build();
		Employee savedEmp4 = employeeService.saveEmployee(emp4);

		Employee emp5 = Employee.builder().employeeName("Susana Bachera").employeeJob(savedBacheroJob).build();
		Employee savedEmp5 = employeeService.saveEmployee(emp5);
		ArrayList<RestoTable> tables = new ArrayList<RestoTable>();
		for (int i = 1; i < 31; i++) {
			RestoTable table = new RestoTable();
			tables.add(table);

		}
		restoTableRepository.saveAll(tables);
	}

	@Test
	@Order(2)
	void registerNewUser() throws Exception {
		this.mockMvc.perform(post("http://localhost:8080/api/v1/arbam/authentication/register").content(
				"{\"name\":\"Mario\",\"lastname\":\"Rojas\",\"username\":\"mar\",\"email\":\"mar@gmail.com\",\"password\":\"1234\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name", is("Mario")))
				.andExpect(jsonPath("$.lastname", is("Rojas"))).andExpect(jsonPath("$.username", is("mar")))
				.andExpect(jsonPath("$.password", is(notNullValue())));
	}

	@Test
	@Order(3)
	void loginUser() throws Exception {
		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/authentication/login")
						.content("{\"username\":\"mar\",\"password\":\"1234\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.username", is("mar"))).andExpect(jsonPath("$.jwtToken", is(notNullValue())))
				.andReturn();

		String[] response = this.mvcResult.getResponse().getContentAsString().split("\"");
		for (String str : response) {
			if (str.length() > 200) {
				this.jwtToken = str;
			}
		}
	}

	private long workingDay1Id;

	@Test
	@Order(4)
	void createWorkingDay() throws Exception {
		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/working_days/")
						.content("{\"totalStartCash\":5000,\"employees\":[1,3]}")
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.totalStartCash", is(5000))).andReturn();

		String[] list = mvcResult.getResponse().getContentAsString().split(",");
		Stream.of(list).forEach(e -> System.out.println(e));
		workingDay1Id = Long.parseLong(Stream.of(list).filter(f -> idPattern.matcher(idRegex).find()).map(m -> m)
				.findFirst().get().replaceAll("[^0-9]", "").strip());

	}

	String idRegex = "(?=.*[0-9]{1})";
	private Pattern idPattern = Pattern.compile(idRegex, Pattern.CASE_INSENSITIVE);

	private long restoTable15Id;

	@Test
	@Order(5)
	void createNewRestoTable15() throws Exception {

		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/resto_tables/open_table")
						.content("{\"id\":1,\"tableNumber\":15,\"employeeId\":1}")
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.employeeName", is("Carla Mesera"))).andExpect(jsonPath("$.open", is(true)))
				.andReturn();

		String[] list = mvcResult.getResponse().getContentAsString().split(",");
		Stream.of(list).forEach(e -> System.out.println(e));
		restoTable15Id = Long.parseLong(Stream.of(list).filter(f -> idPattern.matcher(idRegex).find()).map(m -> m)
				.findFirst().get().replaceAll("[^0-9]", "").strip());

	}

	private long order1Table15Id;

	@Test
	@Order(6)
	void createNewOrder1Table15() throws Exception {
		RestoTableOrderDto orderDto = new RestoTableOrderDto();
		orderDto.setProductId(mainProduct1.getId());
		orderDto.setProductQuantity(2);
		orderDto.setRestoTableId(restoTable15Id);

		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/orders/create_order").content(gson.toJson(orderDto))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.productId", is(mainProduct1.getId().intValue())))
				.andExpect(jsonPath("$.productQuantity", is(2))).andExpect(jsonPath("$.totalOrderPrice", is(3000.00)))
				.andReturn();

		String[] list = mvcResult.getResponse().getContentAsString().split(",");
		Stream.of(list).forEach(e -> System.out.println(e));
		order1Table15Id = Long.parseLong(Stream.of(list).filter(f -> idPattern.matcher(idRegex).find()).map(m -> m)
				.findFirst().get().replaceAll("[^0-9]", "").strip());

	}

	@Test
	@Order(7)
	void updateRestoTablePrice() throws Exception {
		this.mockMvc.perform(
				put("http://localhost:8080/api/v1/arbam/resto_tables/update_price/{id}", gson.toJson(restoTable15Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("id", is((int) restoTable15Id)))
				.andExpect(jsonPath("$.totalTablePrice",
						is(mainProduct1.getProductPrice().getPrice().multiply(new BigDecimal(2)).intValue())));

	}

	@Test
	@Order(8)
	void createNewOrder2() throws Exception {
		RestoTableOrderDto orderDto = new RestoTableOrderDto();
		orderDto.setProductId(mainProduct2.getId());
		orderDto.setProductQuantity(3);
		orderDto.setRestoTableId(restoTable15Id);

		this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/orders/create_order").content(gson.toJson(orderDto))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.productId", is(mainProduct2.getId().intValue())))
				.andExpect(jsonPath("$.productQuantity", is(3))).andExpect(jsonPath("$.totalOrderPrice", is(5400.00)));

		this.mockMvc.perform(
				put("http://localhost:8080/api/v1/arbam/resto_tables/update_price/{id}", gson.toJson(restoTable15Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("id", is((int) restoTable15Id)))
				.andExpect(jsonPath("$.totalTablePrice",
						is(mainProduct1.getProductPrice().getPrice().multiply(new BigDecimal(2))
								.add(mainProduct2.getProductPrice().getPrice().multiply(new BigDecimal(3)))
								.intValue())));

	}

	@Test
	@Order(9)
	void updateOrder1() throws Exception {
		RestoTableOrderDto orderDto = new RestoTableOrderDto();
		orderDto.setProductId(mainProduct1.getId());
		orderDto.setProductQuantity(2);
		orderDto.setRestoTableId(restoTable15Id);

		this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/orders/create_order").content(gson.toJson(orderDto))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is((int) order1Table15Id)))
				.andExpect(jsonPath("$.productId", is(mainProduct1.getId().intValue())))
				.andExpect(jsonPath("$.productQuantity", is(4))).andExpect(jsonPath("$.totalOrderPrice", is(6000.00)));

		this.mockMvc.perform(
				put("http://localhost:8080/api/v1/arbam/resto_tables/update_price/{id}", gson.toJson(restoTable15Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("id", is((int) restoTable15Id)))
				.andExpect(jsonPath("$.totalTablePrice",
						is(mainProduct1.getProductPrice().getPrice().multiply(new BigDecimal(4))
								.add(mainProduct2.getProductPrice().getPrice().multiply(new BigDecimal(3)))
								.intValue())));

	}

	@Test
	@Order(10)
	void deleteOrder1Product() throws Exception {
		this.mockMvc
				.perform(delete("http://localhost:8080/api/v1/arbam/orders/{id}", gson.toJson(order1Table15Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", is("Se elimino el producto")));

		this.mockMvc.perform(
				put("http://localhost:8080/api/v1/arbam/resto_tables/update_price/{id}", gson.toJson(restoTable15Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("id", is((int) restoTable15Id)))
				.andExpect(jsonPath("$.totalTablePrice",
						is(mainProduct1.getProductPrice().getPrice().multiply(new BigDecimal(3))
								.add(mainProduct2.getProductPrice().getPrice().multiply(new BigDecimal(3)))
								.intValue())));
	}

	@Test
	@Order(11)
	void checkOrderStatus() throws Exception {
		RestoTableOrder order1 = restoTableOrderRepository.findById(order1Table15Id).get();
		assertThat(order1.getProductQuantity()).isEqualTo(3);
		assertThat(order1.getTotalOrderPrice().doubleValue()).isEqualTo(4500.00);

	}

	private long restoTable8Id;

	@Test
	@Order(12)
	void createNewRestoTable8() throws Exception {

		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/resto_tables/open_table")
						.content("{\"id\":2,\"tableNumber\":8,\"employeeId\":1}")
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.employeeName", is("Carla Mesera"))).andExpect(jsonPath("$.open", is(true)))
				.andReturn();
		String[] list = mvcResult.getResponse().getContentAsString().split(",");
		this.restoTable8Id = Long.parseLong(Stream.of(list).filter(f -> idPattern.matcher(idRegex).find()).map(m -> m)
				.findFirst().get().replaceAll("[^0-9]", "").strip());

	}

	private long order3Table8Id;

	@Test
	@Order(13)
	void createNewOrder3Table8() throws Exception {
		RestoTableOrderDto orderDto = new RestoTableOrderDto();
		orderDto.setProductId(mainProduct3.getId());
		orderDto.setProductQuantity(3);
		orderDto.setRestoTableId(restoTable8Id);

		this.mvcResult = this.mockMvc
				.perform(post("http://localhost:8080/api/v1/arbam/orders/create_order").content(gson.toJson(orderDto))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.productId", is(mainProduct3.getId().intValue())))
				.andExpect(jsonPath("$.productQuantity", is(3))).andExpect(jsonPath("$.totalOrderPrice", is(5100.00)))
				.andReturn();

		String[] list = mvcResult.getResponse().getContentAsString().split(",");
		Stream.of(list).forEach(e -> System.out.println(e));
		order3Table8Id = Long.parseLong(Stream.of(list).filter(f -> idPattern.matcher(idRegex).find()).map(m -> m)
				.findFirst().get().replaceAll("[^0-9]", "").strip());

		this.mockMvc.perform(
				put("http://localhost:8080/api/v1/arbam/resto_tables/update_price/{id}", gson.toJson(restoTable8Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("id", is((int) restoTable8Id)))
				.andExpect(jsonPath("$.totalTablePrice",
						is(mainProduct3.getProductPrice().getPrice().multiply(new BigDecimal(3)).intValue())));
	}

	double tableClosed15CashTotal;
	double tableClosed15MPTotal;
	@Test
	@Order(14)
	void closeRestoTable15() throws Exception {
		RestoTable table15 = restoTableRepository.findById(restoTable15Id).get();
		Employee employee = employeeService.findEmployeeById(table15.getEmployee().getId());

		List<OrderPaymentMethodDto> paymentDtos = new ArrayList<OrderPaymentMethodDto>();
		paymentDtos.add(getOrderPaymentMethodDto(table15, "efectivo", List.of(mainProduct1.getId())));
		paymentDtos.add(getOrderPaymentMethodDto(table15, "mercado pago", List.of(mainProduct2.getId())));

		this.mvcResult = this.mockMvc
				.perform(put("http://localhost:8080/api/v1/arbam/resto_tables/close_table")
						.content(gson.toJson(paymentDtos)).param("restoTableId", gson.toJson(restoTable15Id))
						.param("workingDayId", gson.toJson(workingDay1Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue()))).andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		RestoTableClosedDto restoTableClosedDto = new ObjectMapper().readValue(json, RestoTableClosedDto.class);
		
		
		tableClosed15CashTotal = restoTableClosedDto.getOrderPaymentMethodResponses().stream()
				.filter(f -> f.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("efectivo")).findFirst().get()
				.getPaymentTotal().doubleValue();
		
		assertThat(tableClosed15CashTotal)
				.isEqualTo(mainProduct1.getProductPrice().getPrice().multiply(new BigDecimal(3)).doubleValue());
		
		tableClosed15MPTotal = restoTableClosedDto.getOrderPaymentMethodResponses().stream()
		.filter(f -> f.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("mercado pago")).findFirst().get()
		.getPaymentTotal().doubleValue();
		
		assertThat(tableClosed15MPTotal)
				.isEqualTo(mainProduct2.getProductPrice().getPrice().multiply(new BigDecimal(3)).doubleValue());
	}
	double tableClosed8CashTotal;
	@Test
	@Order(15)
	void closeRestoTable8() throws Exception {
		RestoTable table8 = restoTableRepository.findById(restoTable8Id).get();
		Employee employee = employeeService.findEmployeeById(table8.getEmployee().getId());

		List<OrderPaymentMethodDto> paymentDtos = new ArrayList<OrderPaymentMethodDto>();
		paymentDtos.add(getOrderPaymentMethodDto(table8, "efectivo", List.of(mainProduct3.getId())));
		
		this.mvcResult = this.mockMvc
				.perform(put("http://localhost:8080/api/v1/arbam/resto_tables/close_table")
						.content(gson.toJson(paymentDtos)).param("restoTableId", gson.toJson(restoTable8Id))
						.param("workingDayId", gson.toJson(workingDay1Id))
						.header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(notNullValue()))).andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		RestoTableClosedDto restoTableClosedDto = new ObjectMapper().readValue(json, RestoTableClosedDto.class);
		
		tableClosed8CashTotal = restoTableClosedDto.getOrderPaymentMethodResponses().stream()
				.filter(f -> f.getPaymentMethod().getPaymentMethod().equalsIgnoreCase("efectivo")).findFirst().get()
				.getPaymentTotal().doubleValue();
		assertThat(tableClosed8CashTotal)
				.isEqualTo(mainProduct3.getProductPrice().getPrice().multiply(new BigDecimal(3)).doubleValue());
		
		
	}
	@Test
	@Order(16)
	void closeWorkingDay()throws Exception{
		this.mvcResult = this.mockMvc.perform(get("http://localhost:8080/api/v1/arbam/working_days/close/{id}",gson.toJson(workingDay1Id))
		 .header("Authorization", "Bearer " + this.jwtToken).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn();
		String json = mvcResult.getResponse().getContentAsString();
		WorkingDayDto workingDayDto = new ObjectMapper().readValue(json, WorkingDayDto.class);
		
		assertThat(workingDayDto.getTotalCash().doubleValue()).isEqualTo(tableClosed15CashTotal +tableClosed8CashTotal);
		assertThat(workingDayDto.getTotalMP().doubleValue()).isEqualTo(tableClosed15MPTotal);
		assertThat(workingDayDto.getTotalWorkingDay().doubleValue())
		.isEqualTo(tableClosed15CashTotal + tableClosed8CashTotal + tableClosed15MPTotal);
	}

	private  OrderPaymentMethodDto getOrderPaymentMethodDto(RestoTable restoTable, String strPaymentMethod,
			List<Long> productIds) {
		List<String> strPayments = List.of(strPaymentMethod);
		return strPayments.stream().map(strPayment -> {
			return mapOrderPaymentMethodToDto(restoTable, strPayment, productIds);
		}).findFirst().get();
	}

	private OrderPaymentMethodDto mapOrderPaymentMethodToDto(RestoTable restoTable, String strPayment,
			List<Long> productIds) {

		OrderPaymentMethodDto paymentDto = new OrderPaymentMethodDto();
		PaymentMethod efectivo = paymentMethodRepository.findByPaymentMethod(strPayment).get();
		paymentDto.setPaymentMethod(mapPaymentToDto(efectivo));
		List<RestoTableOrderDto> orderDtos = new ArrayList<RestoTableOrderDto>();
		restoTableOrderRepository.findAllByRestoTableId(restoTable.getId()).stream().forEach(order -> {

			if (order.getProductQuantity() > 1) {
				setOrderWithProductQuantityGreaterThan1(order, productIds, orderDtos);

			} else {
				setOrderWithSingleProductQuantity(order, productIds, orderDtos);
			}
		});

		paymentDto.setOrders(orderDtos);
		double result = orderDtos.stream().mapToDouble(order -> order.getTotalOrderPrice().doubleValue()).sum();
		paymentDto.setPaymentTotal(new BigDecimal(result));
		return paymentDto;
	}
	
	private static void setOrderWithProductQuantityGreaterThan1(RestoTableOrder order,List<Long> productIds,List<RestoTableOrderDto> orderDtos) {
		for (int i = 0; i < order.getProductQuantity(); i++) {

			productIds.stream().forEach(productId -> {
				if (order.getProduct().getId() == productId) {
					order.setProductQuantity(1);
					order.setTotalOrderPrice(
							order.getTotalOrderPrice().divide(new BigDecimal(order.getProductQuantity())));
					orderDtos.add(RestoTableOrderMapper.INSTANCE.toOrderDto(order));
				}
			});

		}
	}
	
	private static void setOrderWithSingleProductQuantity(RestoTableOrder order,List<Long> productIds,List<RestoTableOrderDto> orderDtos) {
		productIds.stream().forEach(productId -> {
			if (order.getProduct().getId() == productId) {
				orderDtos.add(RestoTableOrderMapper.INSTANCE.toOrderDto(order));
			}
		});
	}

	private static PaymentMethodDto mapPaymentToDto(PaymentMethod paymentMethod) {
		PaymentMethodDto dto = new PaymentMethodDto();
		dto.setId(paymentMethod.getId());
		dto.setPaymentMethod(paymentMethod.getPaymentMethod());
		return dto;
	}

}
