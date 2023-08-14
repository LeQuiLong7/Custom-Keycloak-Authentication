package com.lql.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lql.demo.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;

    @GetMapping("/login-status")
    public String loginStatus(Boolean error) {
        return error ? "Loi" : "ok";
    }

    @PostMapping("/login")
    public AccessTokenResponse login(String username, String password) {
        return userService.grantToken(username, password);

    }

    @PostMapping("/register")
    public Object createUser(String username, String password,
                             @RequestParam(required = false, defaultValue = "") String email,
                             @RequestParam(required = false, defaultValue = "") String firstname,
                                 @RequestParam(required = false, defaultValue = "") String lastname) {

        Object newUser = userService.createNewUser(username, password, email, firstname, lastname);
        return
                newUser;
    }


}
