package com.lord.arbam;

import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lord.arbam.models.Role;
import com.lord.arbam.models.User;
import com.lord.arbam.repositories.RoleRepository;
import com.lord.arbam.services.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AuthenticationServiceTest {
	
	@Autowired
	private UserService userService;
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Test
	void testRegister() {
		
		RuntimeException exception = Assertions.assertThrows(PersistentObjectException.class,()->{
			Role adminRole = Role.builder().authority("ADMIN").build();
			Role admin = roleRepository.save(adminRole);
			Set<Role> roles = new HashSet<>();
			roles.add(admin);
			String encodedPass = passwordEncoder.encode("123");
			User user = User.builder().name("Cholo").username("car").lastname("moro").email("car@gmail.com").authorities(roles).password(encodedPass).enabled(true).build();
			User savedUser = userService.saveUser(user);
		},"exception not throw");
		
		assertTrue(exception.getMessage().equals("detached entity passed to persist: com.lord.arbam.models.Role"));
	
		
		
	}

	
	
}
