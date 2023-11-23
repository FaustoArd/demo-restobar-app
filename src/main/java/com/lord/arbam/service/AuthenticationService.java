package com.lord.arbam.service;

import com.lord.arbam.dto.LoginResponseDto;
import com.lord.arbam.model.User;

public interface AuthenticationService {

	public User register(User user);
	
	public LoginResponseDto login(User user);
}
