package com.se.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.util.Date;

public class JwtUtil {
    static final String SECRET = "${jwt.secret}";
    public static String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(getSigningKey())
                .compact();
    }
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public static boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
    public static boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
