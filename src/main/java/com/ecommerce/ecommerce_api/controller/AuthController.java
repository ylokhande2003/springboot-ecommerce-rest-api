package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.entity.User;
import com.ecommerce.ecommerce_api.security.JwtUtil;
import com.ecommerce.ecommerce_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User dbUser = userService.findByEmail(user.getEmail()).orElse(null);

        if (dbUser != null &&
                passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {

            return jwtUtil.generateToken(dbUser.getEmail());
        }

        return "Invalid credentials";
    }
}