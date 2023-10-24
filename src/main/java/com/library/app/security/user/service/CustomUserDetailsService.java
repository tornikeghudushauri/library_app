package com.library.app.security.user.service;

import com.library.app.security.user.repository.UserRepository;
import com.library.app.security.user.repository.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByUsername(username);
        if (user != null) {
            SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(user.getRole().toString());

            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singleton(authorities)
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
