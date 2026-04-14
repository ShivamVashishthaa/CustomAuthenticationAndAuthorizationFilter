package com.filter.jwtauthentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class JwtAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthenticationApplication.class, args);
    }

}
