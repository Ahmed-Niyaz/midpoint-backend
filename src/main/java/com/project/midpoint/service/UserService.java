package com.project.midpoint.service;

import com.project.midpoint.DTOs.UserResponse;
import com.project.midpoint.model.Users;
import com.project.midpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserResponse verifyUser(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            Users existingUser = userRepository.findByUsername(user.getUsername());

            UserResponse response = new UserResponse();
            response.setUserId(existingUser.getId());
            response.setToken(token);
            response.setUsername(existingUser.getUsername());
            response.setEmail(existingUser.getEmail());
            System.out.println(response);
            return response;
        }

        throw new RuntimeException("Failed to login");
    }
}
