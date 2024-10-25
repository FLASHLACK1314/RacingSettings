package com.flashlack.homeofesportsracingsimulatorsettings.util;

import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT令牌登录工具
 *
 * @author FLASHLACK
 */
@Component
@Slf4j
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
    public static String generateToken(String userUuid) {
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
     * 解析并验证 JWT 令牌
     *
     * @param token JWT 令牌
     * @return 解析后的 JWS（包含 JWT 的 Claims）
     */
    public Jws<Claims> parseToken(String token) {
        try {
            // 解析 JWT 并返回 Jws 对象
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            // 捕获令牌过期异常
            log.error("JWT 令牌已过期：{}", e.getMessage());
            throw new BusinessException("令牌已过期，请重新登录。", ErrorCode.HEADER_ERROR);
        } catch (MalformedJwtException e) {
            // 捕获令牌格式不正确异常
            log.error("JWT 格式不正确：{}", e.getMessage());
            throw new BusinessException("无效的令牌格式。",ErrorCode.HEADER_ERROR);
        } catch (UnsupportedJwtException e) {
            // 捕获不支持的 JWT 异常
            log.error("JWT 令牌使用了不支持的算法：{}", e.getMessage());
            throw new BusinessException("令牌使用了不支持的算法。",ErrorCode.HEADER_ERROR);
        } catch (IllegalArgumentException e) {
            // 捕获非法参数异常
            log.error("JWT 令牌参数异常：{}", e.getMessage());
            throw new BusinessException("非法的令牌参数。",ErrorCode.HEADER_ERROR);
        } catch (JwtException e) {
            // 捕获其他 JWT 异常（如 SecurityException 等）
            log.error("JWT 令牌解析失败：{}", e.getMessage());
            log.info("Token :{}",token);
            throw new BusinessException("令牌解析失败。",ErrorCode.HEADER_ERROR);
        }
    }
}