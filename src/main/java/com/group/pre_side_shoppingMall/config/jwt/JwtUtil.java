package com.group.pre_side_shoppingMall.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long validityInMilliseconds;

    private Key key;

    @PostConstruct
    public void init() {
        // Base64로 인코딩 후 HMAC 키 생성
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedSecretKey.getBytes());
    }

    public String createToken(String userName) {
        Date now = new Date();
        Date expiryTime = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiryTime)
                .signWith(key)
                .compact();
    }

    public String getUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
