package com.lord.arbam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dto.LoginResponseDto;
import com.lord.arbam.dto.RegistrationDto;
import com.lord.arbam.mapper.UserMapper;
import com.lord.arbam.model.User;
import com.lord.arbam.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private  final AuthenticationService authenticationService;
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	
	
	
	@PostMapping("/register")
	ResponseEntity<RegistrationDto> registerUser(@RequestBody RegistrationDto regDto){
		log.info("Registrando usuario controlador");
		User user = UserMapper.INSTANCE.RegDtotoUser(regDto);
		User registeredUser = authenticationService.register(user);
		RegistrationDto completeRegDto = UserMapper.INSTANCE.userToRegDto(registeredUser);
		return new ResponseEntity<RegistrationDto>(completeRegDto, HttpStatus.CREATED);
	}
	@PostMapping("/login")
	ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginResponseDto loginDto){
		log.info("Logeando usuario controlador");
		User user = UserMapper.INSTANCE.LoginDtoToUser(loginDto);
		LoginResponseDto loggedUser = authenticationService.login(user);
		return new ResponseEntity<LoginResponseDto>(loggedUser,HttpStatus.OK);
		
	}

}
