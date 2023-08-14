package com.lql.demo.service;

import com.lql.demo.auth.User;
import com.lql.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepoImpl {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Cacheable("users")
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }
}
