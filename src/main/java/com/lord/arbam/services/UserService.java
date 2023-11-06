package com.lord.arbam.services;

import com.lord.arbam.models.User;

public interface UserService {
	
	public User findByUsername(String username);
	
	public User findUserById(Long id);
	
	public User saveUser(User user);
	
	public void deleteUser(Long id);

}
