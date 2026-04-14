package com.filter.jwtauthentication.controller;

import com.filter.jwtauthentication.config.security.manager.JwtAuthenticationManager;
import com.filter.jwtauthentication.config.security.provider.JwtAuthenticationProvider;
import com.filter.jwtauthentication.entity.RefreshToken;
import com.filter.jwtauthentication.Request.AuthRequestDto;
import com.filter.jwtauthentication.Response.JwtResponseDto;
import com.filter.jwtauthentication.Request.RefreshTokenRequestDto;
import com.filter.jwtauthentication.repository.RefreshTokenRepository;
import com.filter.jwtauthentication.service.jwt.JwtService;
import com.filter.jwtauthentication.service.jwt.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth/v1")
public class TokenController {
    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticationAndGetToken(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authenticate = jwtAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(), authRequestDto.getPassword()
                ));
        if (authenticate.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.generateRefreshTokenByUsername(authRequestDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder()
                    .accessToken(jwtService.generateJwtTokenByUsername(authRequestDto.getUsername()))
                    .refreshToken(refreshToken.getToken()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Exception in User Service ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDto refreshTokenRequest(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return refreshTokenRepository.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateJwtTokenByUsername(user.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDto.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB "));
    }
}
