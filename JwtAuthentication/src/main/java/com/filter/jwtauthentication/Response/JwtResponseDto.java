package com.filter.jwtauthentication.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
}
