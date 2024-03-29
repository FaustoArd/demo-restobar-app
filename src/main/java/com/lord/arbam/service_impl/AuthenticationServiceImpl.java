package com.lord.arbam.service_impl;

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

import com.lord.arbam.dto.LoginResponseDto;
import com.lord.arbam.model.Role;
import com.lord.arbam.model.User;
import com.lord.arbam.repository.RoleRepository;
import com.lord.arbam.service.AuthenticationService;
import com.lord.arbam.service.JwtTokenService;
import com.lord.arbam.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
	
	
	

	@Override
	public User register(User user)  {
		log.info("Register new user");
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		Role userRole = roleRepository.save(Role.builder().authority("USER").build());
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
		return  userService.saveUser(newUser);
		
		}

	@Override
	public LoginResponseDto login(User user)throws AuthenticationException {
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			
			String jwtToken = jwtTokenService.generateJwt(auth);
			User loggedUser = userService.findByUsername(user.getUsername());
			LoginResponseDto loginDto = new LoginResponseDto();
			loginDto.setId(loggedUser.getId());
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
