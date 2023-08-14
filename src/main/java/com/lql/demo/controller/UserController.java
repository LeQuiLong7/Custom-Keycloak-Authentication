package com.lql.demo.controller;

import com.lql.demo.auth.User;
import com.lql.demo.service.UserRepoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserRepoImpl userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.saveUser(user);
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.getAll();
    }

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('READ')")
    public String home(Authentication authentication) {

        return "Welcome to my app " + authentication.getName();
    }


}
