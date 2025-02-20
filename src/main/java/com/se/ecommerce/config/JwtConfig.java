package com.se.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    private static String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtConfig.secret = secret;
    }

    public static String getSecret() {
        return secret;
    }

}
