package com.project.midpoint.controller;

import com.project.midpoint.DTOs.UserResponse;
import com.project.midpoint.model.Users;
import com.project.midpoint.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        return "Hello, welcome to home page" + request.getSession().getId();
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody Users user) {
        System.out.println(user);
        return userService.verifyUser(user);
    }
}
