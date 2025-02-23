package com.se.ecommerce.service;

import com.se.ecommerce.dto.user.UserCreateRequest;
import com.se.ecommerce.dto.user.UserLoginRequest;
import com.se.ecommerce.dto.user.UserLoginResponse;
import com.se.ecommerce.model.Cart;
import com.se.ecommerce.model.Role;
import com.se.ecommerce.repository.UserRepository;
import com.se.ecommerce.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartService cartService;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, CartService cartService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }
    public ResponseEntity<UserLoginResponse> login(UserLoginRequest userLoginRequest){
        log.info("Login request received");
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authentication successful");
        String jwtToken = JwtUtil.generateToken((User) authentication.getPrincipal());
        log.info("JWT token received");
        return ResponseEntity.ok(new UserLoginResponse(jwtToken));
    }
    @Transactional
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
            log.info("New user created");
            cartService.createCart();
            userRepository.save(newUser);
            return login( new UserLoginRequest(userCreateRequest.getEmail(),userCreateRequest.getPassword()));
        }
        //Todo Special Exception
        throw new RuntimeException("User already exists");
    }
}
