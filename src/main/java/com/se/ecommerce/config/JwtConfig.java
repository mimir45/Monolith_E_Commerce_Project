package com.se.ecommerce.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Getter
    private static String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtConfig.secret = secret;
    }

}
