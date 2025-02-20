package com.se.ecommerce.service;

import com.se.ecommerce.dto.UserCreateRequest;
import com.se.ecommerce.dto.UserLoginRequest;
import com.se.ecommerce.dto.UserLoginResponse;
import com.se.ecommerce.model.Role;
import com.se.ecommerce.repository.UserRepository;
import com.se.ecommerce.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest){
        log.info("Login request received");
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = JwtUtil.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new UserLoginResponse(jwtToken));
    }
    public ResponseEntity<UserLoginResponse> register(UserCreateRequest userCreateRequest){
        log.info("Register request received");
        Optional<com.se.ecommerce.model.User> user = userRepository.findByEmail(userCreateRequest.getEmail());
        if(user.isEmpty()){
            com.se.ecommerce.model.User newUser = com.se.ecommerce.model.User.builder()
                    .email(userCreateRequest.getEmail())
                    .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                    .username(userCreateRequest.getUserName())
                    .role(Role.USER)
                    .build();
        }
    }
}
