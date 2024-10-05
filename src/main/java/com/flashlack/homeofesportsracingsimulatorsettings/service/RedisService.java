package com.flashlack.homeofesportsracingsimulatorsettings.service;

/**
 * Redis 接口
 * @author FLASHLACK
 */
public interface RedisService {
    /**
     * 将用户的 JWT 令牌保存到 Redis 中，并设置过期时间为 1 小时
     *
     * @param userUuid 用户UUID
     * @param token JWT 令牌
     */
    void saveTokenToRedis(
            String userUuid,
            String token
    );
    /**
     * 从 Redis 中删除指定用户的 JWT 令牌
     *
     * @param userUuid 用户UUID
     */
    void deleteTokenFromRedis(
            String userUuid
    );
    /**
     * 从 Redis 中获取指定用户的 JWT 令牌
     *
     * @param userUuid 用户UUID
     * @return JWT 令牌
     */
    String getTokenFromRedis(
            String userUuid
    );

}
