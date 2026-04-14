package com.filter.jwtauthentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
