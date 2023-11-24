package com.lord.arbam.service;

import org.springframework.security.core.Authentication;

public interface JwtTokenService {
	
	public String generateJwt(Authentication auth);

}
