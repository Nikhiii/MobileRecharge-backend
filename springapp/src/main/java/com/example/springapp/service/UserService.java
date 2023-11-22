package com.example.springapp.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.springapp.model.User;

public interface UserService {

	public boolean registerUser(User user);
	
	public User getUserByEmail(Long userId);
	
	public UserDetails loadUserByUsername(String username);

	public User getUserById(Long userId);
}
