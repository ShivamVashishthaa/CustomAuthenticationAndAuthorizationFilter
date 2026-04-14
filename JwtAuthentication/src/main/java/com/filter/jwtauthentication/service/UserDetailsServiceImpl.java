package com.filter.jwtauthentication.service;

import com.filter.jwtauthentication.config.helperclasses.UserDetailsImp;

import com.filter.jwtauthentication.dto.AuthorityDto;
import com.filter.jwtauthentication.entity.Authority;
import com.filter.jwtauthentication.entity.User;
import com.filter.jwtauthentication.dto.UserDto;
import com.filter.jwtauthentication.repository.AuthorityRepository;
import com.filter.jwtauthentication.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImp(user);
    }

    private User checkIfUserAlreadyExists(UserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername())
                .orElse(null);
    }

    public Boolean signupUser(UserDto userDto) {
//        User user = mapUserDtoToUser(userDto);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (Objects.nonNull(checkIfUserAlreadyExists(userDto))) {
            return false;
        }
        Set<Authority> userSet = new HashSet<>();
        if (userDto.getAuthoritiesDto() != null && !userDto.getAuthoritiesDto().isEmpty()) {
            List<Authority> allAuthorities = authorityRepository.findAll();
            for (AuthorityDto authorityDto : userDto.getAuthoritiesDto()) {
                for (Authority authority : allAuthorities) {
                    if (authority.getName().equalsIgnoreCase(authorityDto.getName())) {
                        userSet.add(authority);
                    }
                }
            }
        }
        String userId = UUID.randomUUID().toString();

        userRepository.save(User.builder()
                .id(userId)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .authorities(userSet)
                .build());
        return true;
    }


    public void updateUser(User user) {

    }

    public void deleteUser(String username) {

    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public List<User> authenticatedUsers() {
        return userRepository.findAll();
    }

    private User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }


}
