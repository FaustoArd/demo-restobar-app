package com.lord.arbam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lord.arbam.model.Employee;
import com.lord.arbam.model.EmployeeJob;
import com.lord.arbam.model.Ingredient;
import com.lord.arbam.model.IngredientCategory;
import com.lord.arbam.model.PaymentMethod;
import com.lord.arbam.model.Product;
import com.lord.arbam.model.ProductCategory;
import com.lord.arbam.model.IngredientMix;
import com.lord.arbam.model.ProductPrice;
import com.lord.arbam.model.ProductStock;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableOrder;
import com.lord.arbam.model.Role;
import com.lord.arbam.model.User;
import com.lord.arbam.repository.EmployeeJobRepository;
import com.lord.arbam.repository.PaymentMethodRepository;
import com.lord.arbam.repository.RestoTableRepository;
import com.lord.arbam.repository.RoleRepository;
import com.lord.arbam.repository.UserRepository;
import com.lord.arbam.service.AuthenticationService;
import com.lord.arbam.service.CategoryService;
import com.lord.arbam.service.EmployeeService;
import com.lord.arbam.service.IngredientService;
import com.lord.arbam.service.IngredientMixService;
import com.lord.arbam.service.ProductPriceService;
import com.lord.arbam.service.ProductService;
import com.lord.arbam.service.ProductStockService;
import com.lord.arbam.service.RestoTableOrderService;
import com.lord.arbam.service.RestoTableService;
import com.lord.arbam.service.UserService;

@SpringBootApplication
public class ArbamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbamApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ProductService productService, CategoryService<ProductCategory> productCategoryService,
			CategoryService<IngredientCategory> ingredientCategoryService, IngredientService ingredientService,
			ProductStockService productStockService, ProductPriceService productPriceService,
			IngredientMixService productMixService,RestoTableService restoTableService,RestoTableRepository restoTableRepository,
			RestoTableOrderService restoTableOrderService, EmployeeService employeeService,
			EmployeeJobRepository employeeJobRepository,RoleRepository roleRepository,
			AuthenticationService authService, PasswordEncoder encoder,UserService userService,PaymentMethodRepository paymentMethodRepository) {
		return args -> {
			
			Role role = Role.builder().authority("USER").build();
			User user = User.builder().name("carlos").lastname("rodo").email("car@gmail.om").username("car").build();
			String encodedPassword = encoder.encode("123");
			Role userRole = roleRepository.save(Role.builder().authority("ADMIN").build());
			Set<Role> authorities = new HashSet<Role>();
			authorities.add(userRole);
			User newUser = User.builder()
					.name(user.getName())
					.username(user.getUsername())
					.lastname(user.getLastname())
					.enabled(true)
					.email(user.getEmail())
					.password(encodedPassword)
					.authorities(authorities).build();
			 userService.saveUser(newUser);
			
			
			
			
			
			//User user =  User.builder().name("carlos").lastname("mede").username("car").email("car@mail.com").password("123").build();
			//User savedUser = 
			
			//User user = User.builder().name("Carlos").lastname("Marino").username("car").email("car@gmail.com").password("1234").build();
			//authService.registerUser(user);
			
			 
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
			productMixService.saveIngredientMix(mix1);
			IngredientMix mix2 = IngredientMix.builder().ingredient(savedPimienta).product(savedProduct1).ingredientAmount(300).build();
			productMixService.saveIngredientMix(mix2);
			
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
			
			/*RestoTableOrder order1 = RestoTableOrder.builder().product(savedProductstocked1)
					.productQuantity(1).totalOrderPrice(savedProductstocked1.getProductPrice().getPrice() *1).build();
			RestoTableOrder savedOrder1 = restoTableOrderService.createOrder(order1);
			
			RestoTableOrder order2 = RestoTableOrder.builder().product(savedProductstocked3)
					.productQuantity(1).totalOrderPrice(savedProductstocked3.getProductPrice().getPrice() *1).build();
			RestoTableOrder savedOrder2 =  restoTableOrderService.createOrder(order2);
			
			
			RestoTable table1 = RestoTable.builder().open(true).tableNumber(1).employee(savedEmp1).tableOrder(savedOrder1)
					.totalTablePrice(savedProductstocked1.getProductPrice().getPrice()).build();
			restoTableService.createRestoTable(table1);
			
			RestoTable table2 = RestoTable.builder().open(true).tableNumber(2).employee(savedEmp1).tableOrder(savedOrder2)
					.totalTablePrice(savedProductstocked1.getProductPrice().getPrice() * savedProductstocked3.getProductPrice().getPrice()).build();
			restoTableService.createRestoTable(table2);*/
			
			
		};
	}

}
