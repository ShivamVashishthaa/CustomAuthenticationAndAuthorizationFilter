package com.filter.authenticationandauthorizationfilter.config.security.provider;

import com.filter.authenticationandauthorizationfilter.config.security.authentication.CustomAuthentication;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
    @Value("${my.secret.key}")
    private String key;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthentication ca = (CustomAuthentication) authentication;
        String headerKey = ca.getKey();
        if (key.equals(headerKey)) {
            return new CustomAuthentication(true, null);
        } else {
            throw new BadCredentialsException(":(");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
