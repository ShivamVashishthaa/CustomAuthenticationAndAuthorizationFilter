package com.filter.jwtauthentication.config.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
    private final List<AuthenticationProvider> providers;

    public JwtAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        for (AuthenticationProvider provider : providers) {
            if (provider.supports(authentication.getClass())) {
                Authentication result = provider.authenticate(authentication);
                if (result != null) return result; // ✅ success
            }
        }
        throw new BadCredentialsException("Authentication failed");
    }
}

