package com.qqsystem.serve.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    // 必须至少32位
    private static final String SECRET = "qqsystem_jwt_secret_key_1234567890";

    private static final long EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7天

    // ⭐ 统一Key（不要重复生成）
    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 生成 token
     */
    public static String generateToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(username)              // 用户名
                .claim("userId", userId)          // 用户ID
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 token
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取 userId（正确写法）
     */
    public static Long getUserId(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    /**
     * 获取 username
     */
    public static String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 校验 token
     */
    public static boolean isValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}