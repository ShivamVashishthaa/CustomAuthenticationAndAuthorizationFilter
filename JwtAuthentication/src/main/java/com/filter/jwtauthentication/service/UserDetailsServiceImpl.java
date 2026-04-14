package com.filter.jwtauthentication.service;

import com.filter.jwtauthentication.config.helperclasses.UserDetailsImp;

import com.filter.jwtauthentication.model.User;
import com.filter.jwtauthentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImp(user);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.getAuthorities().forEach(user::addAuthority);
        return userRepository.save(user);
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

    public List<User> authenticatedUsers(){
        return userRepository.findAll();
    }
}
