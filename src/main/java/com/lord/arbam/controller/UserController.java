package com.lord.arbam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lord.arbam.dto.UserDto;
import com.lord.arbam.mapper.UserMapper;
import com.lord.arbam.model.User;
import com.lord.arbam.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/users")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	private final UserService userService;
	
	@GetMapping("/{id}")
	ResponseEntity<UserDto> findUserById(@PathVariable("id")Long id){
		User user = userService.findUserById(id);
		UserDto userDto = UserMapper.INSTANCE.toUserDto(user);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	

}
