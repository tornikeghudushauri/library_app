package com.library.app.security.user.service;

import com.library.app.security.user.repository.UserRepository;
import com.library.app.security.user.repository.entity.UserEntity;

import com.library.app.security.user.boundary.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The `UserService` class provides user-related services, such as user registration, retrieval, and authentication.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userRepository.save(user);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
