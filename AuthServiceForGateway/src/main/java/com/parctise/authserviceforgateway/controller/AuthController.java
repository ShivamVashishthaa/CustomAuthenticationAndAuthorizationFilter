package com.parctise.authserviceforgateway.controller;

import com.parctise.authserviceforgateway.entity.User;
import com.parctise.authserviceforgateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        service.signup(user);
        return "User created";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        String token = service.login(user.getUsername(), user.getPassword());
        return Map.of("accessToken", token);
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout success (client removes token)";
    }
}
