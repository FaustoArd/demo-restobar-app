package com.lord.arbam;

import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lord.arbam.model.Role;
import com.lord.arbam.model.User;
import com.lord.arbam.repository.RoleRepository;
import com.lord.arbam.service.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AuthenticationServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	void testRegister() {
		
			Role adminRole = Role.builder().authority("ADMIN").build();
			Role admin = roleRepository.save(adminRole);
			Set<Role> roles = new HashSet<>();
			roles.add(admin);
			String encodedPass = passwordEncoder.encode("123");
			User user = User.builder().name("Cholo").username("car").lastname("moro").email("car@gmail.com").authorities(roles).password(encodedPass).enabled(true).build();
			User savedUser = userService.saveUser(user);
			assertEquals(savedUser.getName(), "Cholo");
			assertTrue(savedUser.getPassword()!=null);
			assertEquals(savedUser.getAuthorities().stream().filter(r -> r.getAuthority()=="ADMIN").findFirst().get(),admin);
			assertTrue(savedUser.isEnabled());
		}

	
	
}
