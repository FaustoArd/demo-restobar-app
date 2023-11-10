package com.lord.arbam.services_impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lord.arbam.dtos.LoginResponseDto;
import com.lord.arbam.models.Role;
import com.lord.arbam.models.User;
import com.lord.arbam.repositories.RoleRepository;
import com.lord.arbam.services.AuthenticationService;
import com.lord.arbam.services.JwtTokenService;
import com.lord.arbam.services.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final RoleRepository roleRepository;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private final JwtTokenService jwtTokenService;
	
	public AuthenticationServiceImpl(UserService userService,RoleRepository roleRepository,PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager,JwtTokenService jwtTokenService) {
		this.userService= userService;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
				
	}
	

	@Override
	public User registerUser(User user)  {
		log.info("Register new user");
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		Role userRole = roleRepository.findByAuthority("USER").get();
		Set<Role> authorities = new HashSet<>();
		authorities.add(userRole);
		User newUser = User.builder()
				.name(user.getName())
				.username(user.getUsername())
				.lastname(user.getLastname())
				.enabled(true)
				.email(user.getEmail())
				.password(encodedPassword)
				.authorities(authorities).build();
		User savedUser = userService.saveUser(newUser);
		return savedUser;
		}

	@Override
	public LoginResponseDto loginUser(User user)throws AuthenticationException {
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			
			String jwtToken = jwtTokenService.generateJwt(auth);
			User loggedUser = userService.findByUsername(user.getUsername());
			LoginResponseDto loginDto = new LoginResponseDto();
			loginDto.setUsername(loggedUser.getUsername());
			loginDto.setJwtToken(jwtToken);
			log.info("Login complete. User: " + loggedUser.getUsername());
			return loginDto;
			
		}catch(AuthenticationException ex) {
			log.info("Login error");
			return new LoginResponseDto();
		}
	}

}
