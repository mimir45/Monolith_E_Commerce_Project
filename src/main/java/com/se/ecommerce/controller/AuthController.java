package com.se.ecommerce.controller;

import com.se.ecommerce.dto.UserCreateRequest;
import com.se.ecommerce.dto.UserLoginRequest;
import com.se.ecommerce.dto.UserLoginResponse;
import com.se.ecommerce.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return authService.login(userLoginRequest);
    }
    @PostMapping("/signup")
    public ResponseEntity<UserLoginResponse> signup(@RequestBody UserCreateRequest userCreateRequest) {
        return authService.register(userCreateRequest);
    }


}
