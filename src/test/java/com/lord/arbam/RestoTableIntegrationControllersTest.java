package com.lord.arbam;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.repository.EmployeeJobRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.ProductCategoryRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.EmployeeService;
import com.lord.arbam.service.IngredientMixService;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.ProductService;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestoTableIntegrationControllersTest {
	
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
	private EmployeeService employeeService;
	
	@Autowired
	private ProductService productService;
	
	
	

	@Autowired
	private MockMvc mockMvc;

	private MvcResult mvcResult;

	private String jwtToken;
	
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
	
	@Test
	@Order(4)
	void createWorkingDay()throws Exception{
		this.mockMvc.perform(post("http://localhost:8080/api/v1/arbam/working_days/")
				.content("{\"totalStartCash\":5000,\"employees\":[1,3]}").header("Authorization", "Bearer " + this.jwtToken)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
		.andExpect(jsonPath("$.totalStartCash", is(5000)));
	}

	@Test
	@Order(5)
	void whenCreateNewRestoTable_MustReturnNewRestoTableDto() throws Exception {

		this.mockMvc.perform(post("http://localhost:8080/api/v1/arbam/resto_tables/open_table")
				.content("{\"id\":1,\"tableNumber\":15,\"employeeId\":1}").header("Authorization", "Bearer " + this.jwtToken)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(notNullValue()))).andExpect(jsonPath("$.employeeName", is("Carla Mesera")))
				.andExpect(jsonPath("$.open", is(true)));
	}
	

}
