package com.parctise.authserviceforgateway.service;

import com.parctise.authserviceforgateway.entity.User;
import com.parctise.authserviceforgateway.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtils jwtUtil;

    public void signup(User user) {
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setPassword(user.getPassword());
        repo.save(user);
    }

    public String login(String username, String password) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
        if (!password.equalsIgnoreCase(user.getPassword()))
            throw new RuntimeException("Invalid password");

        return JwtUtils.generateToken(user.getUsername(), user.getRole());
    }
}
