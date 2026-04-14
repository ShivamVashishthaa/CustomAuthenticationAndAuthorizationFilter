package com.filter.authenticationandauthorizationfilter.config.security.manager;

import com.filter.authenticationandauthorizationfilter.config.security.provider.CustomAuthProvider;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthManager implements AuthenticationManager {
    private final CustomAuthProvider customAuthProvider;
    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (customAuthProvider.supports(authentication.getClass())) {
            return customAuthProvider.authenticate(authentication);
        }
        throw new BadCredentialsException(":(");
    }
}
