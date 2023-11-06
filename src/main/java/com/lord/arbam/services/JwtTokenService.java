package com.lord.arbam.services;

import org.springframework.security.core.Authentication;

public interface JwtTokenService {
	
	public String generateJwt(Authentication auth);

}
