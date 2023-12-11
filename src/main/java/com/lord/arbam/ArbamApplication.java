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
	
	

}
