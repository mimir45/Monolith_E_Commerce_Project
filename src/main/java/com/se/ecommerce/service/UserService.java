package com.se.ecommerce.service;

import com.se.ecommerce.model.User;
import com.se.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long userid) {
        Optional<User> user = userRepository.findById(userid);
        return user;

    }
}
