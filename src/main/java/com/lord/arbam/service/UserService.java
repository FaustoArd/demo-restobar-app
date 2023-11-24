package com.lord.arbam.service;

import com.lord.arbam.model.User;

public interface UserService {
	
	public User findByUsername(String username);
	
	public User findUserById(Long id);
	
	public User saveUser(User user);
	
	public void deleteUser(Long id);

}
