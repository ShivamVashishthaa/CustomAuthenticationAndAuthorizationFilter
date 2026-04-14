package com.filter.jwtauthentication.config.helperclasses;


import com.filter.jwtauthentication.entity.Authority;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class GrantedAuthorityIml implements GrantedAuthority {
    private final Authority authority;

    @Override
    public @Nullable String getAuthority() {
        return authority.getName();
    }
}
