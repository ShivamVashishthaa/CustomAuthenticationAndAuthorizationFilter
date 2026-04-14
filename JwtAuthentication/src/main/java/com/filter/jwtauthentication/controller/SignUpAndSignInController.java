package com.filter.jwtauthentication.controller;

import com.filter.jwtauthentication.config.security.manager.JwtAuthenticationManager;
import com.filter.jwtauthentication.model.AuthRequest;
import com.filter.jwtauthentication.model.AuthResponse;
import com.filter.jwtauthentication.model.User;
import com.filter.jwtauthentication.service.UserDetailsServiceImpl;
import com.filter.jwtauthentication.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sign")
public class SignUpAndSignInController {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtService jwtService;
    private JwtAuthenticationManager jwtAuthenticationManager;

    @PostMapping("/up")
    public ResponseEntity<User> singUpUser(@RequestBody User user) {
        return ResponseEntity.ok(userDetailsServiceImpl.createUser(user));
    }

    @PostMapping("/in")
    public ResponseEntity<?> signInUser(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = jwtAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword()
                ));
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String token = jwtService.generateJwtTokenByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
