package com.example.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	private UserRepository userRepo;

	public String passwordEncoder(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	@Override
	public boolean registerUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       String passwordEncoded = passwordEncoder.encode(user.getPassword());
       user.setPassword(passwordEncoded);
       this.userRepo.save(user);
       return true;
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       List<User> users = userRepo.findByEmail(email);

       if (users.isEmpty()) {
           throw new UsernameNotFoundException("User not found with email: " + email);
       }
       User user = users.get(0);
       return org.springframework.security.core.userdetails.User.builder()
               .username(user.getEmail())
               .password(user.getPassword())
               .roles(user.getUserRole())
               .build();
   }

    @Override
    public User getUserByEmail(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }

}