package com.nan19.roleplaying.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key key = Keys.hmacShaKeyFor("aVerySecretKeyForJwtDemo1234567890".getBytes()); // 自己换成更复杂的

    public static String generateToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1天
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Long getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }
}