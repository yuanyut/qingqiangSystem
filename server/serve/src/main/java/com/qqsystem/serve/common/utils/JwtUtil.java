package com.qqsystem.serve.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "qqsystem_jwt_secret_key_1234567890";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成 token
     */
    public static String generateToken(Long userId, String username, String role) {

        return Jwts.builder()
                .subject(username)               // ✔ 主体是 username
                .claim("userId", userId)        // ✔ userId
                .claim("role", role)            // ✔ 权限
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 token
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
//得到id
    public static Long getUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }
//得到username
    public static String getUsername(String token) {
        return parseToken(token).getSubject();
    }
//得到role
    public static String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }
//    校验
    public static boolean isValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}