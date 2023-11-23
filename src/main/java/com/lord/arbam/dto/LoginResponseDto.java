package com.lord.arbam.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private String jwtToken;

}
