package com.filter.jwtauthentication.service.jwt;

import com.filter.jwtauthentication.entity.RefreshToken;
import com.filter.jwtauthentication.entity.User;
import com.filter.jwtauthentication.repository.RefreshTokenRepository;
import com.filter.jwtauthentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // Generate refresh token by username
    public RefreshToken generateRefreshTokenByUsername(String username) {
        User user = userRepository.findByUsername((username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        RefreshToken token = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiration(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiration().compareTo(Instant.now()) < 0) {
            throw new RuntimeException(token.getToken() + " Refresh token is expired, Please login again");
        }
        return token;
    }


}
