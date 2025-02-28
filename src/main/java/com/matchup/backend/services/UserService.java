package com.matchup.backend.services;

import java.util.Optional;
import com.matchup.backend.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.matchup.backend.model.User;
import com.matchup.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private com.matchup.backend.security.UserDetailsServiceImpl userDetailsService;

    private PasswordEncoder passwordEncoder;
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
     public User registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        // Authenticate the user
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );    
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
        

        // Generate JWT token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return jwtUtil.generateToken(userDetails);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

}
