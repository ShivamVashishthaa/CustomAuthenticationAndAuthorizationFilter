package com.filter.jwtauthentication.controller;

import com.filter.jwtauthentication.config.security.manager.JwtAuthenticationManager;
import com.filter.jwtauthentication.entity.RefreshToken;
import com.filter.jwtauthentication.Response.JwtResponseDto;
import com.filter.jwtauthentication.dto.UserDto;
import com.filter.jwtauthentication.service.UserDetailsServiceImpl;
import com.filter.jwtauthentication.service.jwt.JwtService;
import com.filter.jwtauthentication.service.jwt.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private JwtAuthenticationManager jwtAuthenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> singUp(@RequestBody UserDto userDto) {
        try {
            if (Boolean.FALSE.equals(userDetailsServiceImpl.signupUser(userDto))) {
                return new ResponseEntity<>("Already Existed", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.generateRefreshTokenByUsername(userDto.getUsername());
            String token = jwtService.generateJwtTokenByUsername(userDto.getUsername());

            return new ResponseEntity<>(JwtResponseDto.builder().accessToken(token).refreshToken(refreshToken.getToken())
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception in user Service ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
