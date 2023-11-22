package com.example.springapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.security.JwtUtil;
import com.example.springapp.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	private UserService userService;

    @Autowired
    private UserRepository userRepository;

	@PostMapping("/register")
    public boolean handler1(@RequestBody User user){
        return userService.registerUser(user);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> handler2(@RequestBody User user) throws Exception {
    try {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception("bad credentials");
    }

    // Load User from database
    List<User> users = userRepository.findByEmail(user.getEmail());
    if (users.isEmpty()) {
        throw new Exception("User not found");
    }
    User authenticatedUser = users.get(0);
    // Load User from database
    // User authenticatedUser = userRepository.findByEmail(user.getEmail()).get();

    UserDetails userDetails = this.userService.loadUserByUsername(user.getEmail());
    String token = jwtUtil.generateToken(userDetails);

    // Get role from User
    String role = authenticatedUser.getUserRole();
    String name = authenticatedUser.getUsername();

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    response.put("role", role);
    response.put("username", name);

    return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
}
    
}
