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
import com.lord.arbam.models.ProductCategory;
import com.lord.arbam.models.ProductMix;
import com.lord.arbam.models.ProductPrice;
import com.lord.arbam.models.ProductStock;
import com.lord.arbam.models.RestoTable;
import com.lord.arbam.models.RestoTableOrder;
import com.lord.arbam.models.Role;
import com.lord.arbam.models.User;
import com.lord.arbam.repositories.EmployeeJobRepository;
import com.lord.arbam.repositories.RestoTableRepository;
import com.lord.arbam.repositories.RoleRepository;
import com.lord.arbam.models.Employee;
import com.lord.arbam.models.EmployeeJob;
import com.lord.arbam.models.Ingredient;
import com.lord.arbam.models.IngredientCategory;
import com.lord.arbam.models.Product;
import com.lord.arbam.services.AuthenticationService;
import com.lord.arbam.services.EmployeeService;
import com.lord.arbam.services.IngredientCategoryService;
import com.lord.arbam.services.IngredientService;
import com.lord.arbam.services.ProductCategoryService;
import com.lord.arbam.services.ProductMixService;
import com.lord.arbam.services.ProductPriceService;
import com.lord.arbam.services.ProductService;
import com.lord.arbam.services.ProductStockService;
import com.lord.arbam.services.RestoTableOrderService;
import com.lord.arbam.services.RestoTableService;

@SpringBootApplication
public class ArbamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbamApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ProductService productService, ProductCategoryService categoryService,
			IngredientCategoryService ingredientCategoryService, IngredientService ingredientService,
			ProductStockService productStockService, ProductPriceService productPriceService,
			ProductMixService productMixService,RestoTableService restoTableService,RestoTableRepository restoTableRepository,
			RestoTableOrderService restoTableOrderService, EmployeeService employeeService,
			EmployeeJobRepository employeeJobRepository,RoleRepository roleRepository,AuthenticationService authService) {
		return args -> {
			
			Role role = Role.builder().authority("USER").build();
			roleRepository.save(role);
			//User user = User.builder().name("Carlos").lastname("Marino").username("car").email("car@gmail.com").password("1234").build();
			//authService.registerUser(user);
			

			ProductCategory pCategory1 = ProductCategory.builder().categoryName("Pizza").build();
			categoryService.saveCategory(pCategory1);
			ProductCategory pCategory2 = ProductCategory.builder().categoryName("Pollo").build();
			categoryService.saveCategory(pCategory2);
			
			ProductCategory pCategory3 = ProductCategory.builder().categoryName("Cerveza").build();
			categoryService.saveCategory(pCategory3);

			IngredientCategory ingredientCategory1 = IngredientCategory.builder().categoryName("Salsa de tomate").build();
			ingredientCategoryService.saveCategory(ingredientCategory1);
			IngredientCategory ingredientCategory2 = IngredientCategory.builder().categoryName("Micelaneos").build();
			ingredientCategoryService.saveCategory(ingredientCategory2);

			
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

			Ingredient sal = Ingredient.builder().ingredientName("sal").ingredientAmount(5000).build();
			Ingredient savedSal = ingredientService.saveIngredient(savedIngredientCategory3, sal);

			Ingredient pimienta = Ingredient.builder().ingredientName("pimienta").ingredientAmount(4000).build();
			Ingredient savedPimienta = ingredientService.saveIngredient(savedIngredientCategory3, pimienta);
			
			/*ProductMix mix1 = ProductMix.builder().ingredient(savedSal).product(savedProduct1).ingredientAmount(500).build();
			productMixService.saveProductMix(mix1);
			ProductMix mix2 = ProductMix.builder().ingredient(savedPimienta).product(savedProduct1).ingredientAmount(300).build();
			productMixService.saveProductMix(mix2);*/
			
			Product product = productService.findProductById(1L);
			ProductStock stock = new ProductStock(10);
			Product savedProductstocked1 = productService.createProductStock(product, stock);
			
			Product findedProduct2 = productService.findProductById(2L);
			ProductStock stock2 = new ProductStock(20);
			Product savedProductstocked2 = productService.createProductStock(findedProduct2, stock2);
			
			Product findedProduct3 = productService.findProductById(3L);
			ProductStock stock3 = new ProductStock(30);
			Product savedProductstocked3 = productService.createProductStock(findedProduct3, stock3) ;
			EmployeeJob empJob = EmployeeJob.builder().jobRole("Mesero/a").build();
			EmployeeJob savedEmpJob = employeeJobRepository.save(empJob); 
			
			Employee emp1 = Employee.builder().employeeName("Carla").employeeJob(savedEmpJob).build();
			Employee savedEmp1 =  employeeService.saveEmployee(emp1);
			
			Employee emp2 = Employee.builder().employeeName("Marina").employeeJob(savedEmpJob).build();
			Employee savedEmp2 =  employeeService.saveEmployee(emp2);
			
			Employee emp3 = Employee.builder().employeeName("Silvana").employeeJob(savedEmpJob).build();
			Employee savedEmp3 =  employeeService.saveEmployee(emp3);
			
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
