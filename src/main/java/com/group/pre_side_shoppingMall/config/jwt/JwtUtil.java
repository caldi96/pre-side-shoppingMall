package com.group.pre_side_shoppingMall.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
        // secretKey 길이 검증 (HMAC-SHA256은 최소 32바이트 필요)
        if (secretKey.length() < 32) {
            throw new IllegalArgumentException("secret 키는 적어도 32자 이상이어야 합니다");
        }
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
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
        } catch (ExpiredJwtException e) {
            // 토큰 만료
            return false;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            // 잘못된 토큰
            return false;
        } catch (IllegalArgumentException e) {
            // 토큰이 null 이거나 빈 문자열
            return false;
        }
    }
}
