package com.filter.authenticationandauthorizationfilter.config.security;

import com.filter.authenticationandauthorizationfilter.config.security.filters.CustomAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthFilter customAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.addFilterAt(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
//        http.httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/users/signup").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
