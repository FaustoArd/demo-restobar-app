package com.lord.arbam.services;

import com.lord.arbam.dtos.LoginResponseDto;
import com.lord.arbam.models.User;

public interface AuthenticationService {

	public User register(User user);
	
	public LoginResponseDto login(User user);
}
