//package com.parctise.authserviceforgateway.service;
//
//import com.parctise.authserviceforgateway.entity.User;
//import com.parctise.authserviceforgateway.respository.UserRepository;
//import org.jspecify.annotations.NonNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository repo;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(@NonNull String username) {
//
//        User user = repo.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(passwordEncoder.encode(user.getPassword()))
//                .roles(user.getRole())
//                .build();
//    }
//}
//
