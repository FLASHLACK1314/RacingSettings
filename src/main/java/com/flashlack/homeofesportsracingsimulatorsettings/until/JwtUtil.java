package com.flashlack.homeofesportsracingsimulatorsettings.until;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT令牌登录工具
 *
 * @author FLASHLACK
 */
@Component
public class JwtUtil {
    // 定义 JWT 的过期时间（1小时）
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;
    // 定义签名密钥（使用更强的 HMAC-SHA-256 加密算法）
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成 JWT 令牌
     *
     * @param userUuid 用户名（作为 Payload 中的 subject）
     * @return 生成的 JWT 令牌字符串
     */
    public  String generateToken(String userUuid) {
        return Jwts.builder()
                // 设置主题，即用户身份标识
                .setSubject(userUuid)
                // 签发时间
                .setIssuedAt(new Date())
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // 使用 HS256 算法签名
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                // 生成 JWT 字符串
                .compact();
    }

    /**
     * 从 JWT 中解析用户Uuid
     *
     * @param token JWT 令牌
     * @return 解析出的用户名
     */
    public  String getUserUuidFromToken(String token) {
        return parseToken(token).getBody().getSubject();
    }

    /**
     * 验证 JWT 令牌是否合法
     *
     * @param token JWT 令牌
     * @return 合法则返回 true，否则返回 false
     */
    public boolean validateToken(String token) {
        try {
            // 如果解析失败会抛出异常
            parseToken(token);
            return true;
        } catch (JwtException e) {
            // 解析失败，说明令牌不合法或已过期
            return false;
        }
    }

    /**
     * 解析 JWT 令牌
     *
     * @param token JWT 令牌
     * @return 解析后的 JWS（带签名的 JWT）
     */
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                // 设置密钥
                .setSigningKey(SECRET_KEY)
                .build()
                // 解析 JWT
                .parseClaimsJws(token);
    }
}