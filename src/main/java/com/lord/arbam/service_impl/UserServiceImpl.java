package com.lord.arbam.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lord.arbam.exception.UserNotFoundException;
import com.lord.arbam.model.User;
import com.lord.arbam.repository.UserRepository;
import com.lord.arbam.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private final UserRepository userRepository;

	

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No se encontro el nombre de usuario"));
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No se encontro el usuario"));
	}

	@Override
	public User saveUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(Long id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
		}else {
			throw new UserNotFoundException("No se encontro el usuario");
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByUsername(username);
	}

}
