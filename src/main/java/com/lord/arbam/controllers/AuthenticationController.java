package com.lord.arbam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dtos.LoginResponseDto;
import com.lord.arbam.dtos.RegistrationDto;
import com.lord.arbam.mappers.UserMapper;
import com.lord.arbam.models.User;
import com.lord.arbam.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/arbam/authentication")
public class AuthenticationController {
	
	@Autowired
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/register")
	ResponseEntity<RegistrationDto> registerUser(@RequestBody RegistrationDto regDto){
		User user = UserMapper.INSTANCE.RegDtotoUser(regDto);
		User registeredUser = authenticationService.registerUser(user);
		RegistrationDto completeRegDto = UserMapper.INSTANCE.userToRegDto(registeredUser);
		return new ResponseEntity<RegistrationDto>(completeRegDto, HttpStatus.CREATED);
	}
	@PostMapping("/login")
	ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginResponseDto loginDto){
		User user = UserMapper.INSTANCE.LoginDtoToUser(loginDto);
		LoginResponseDto loggedUser = authenticationService.loginUser(user);
		return new ResponseEntity<LoginResponseDto>(loggedUser,HttpStatus.OK);
		
	}

}