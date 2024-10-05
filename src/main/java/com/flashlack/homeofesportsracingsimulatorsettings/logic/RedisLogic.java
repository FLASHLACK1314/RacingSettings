package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis 登录逻辑
 * @author FLASHLACK
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RedisLogic implements RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveTokenToRedis(String userUuid, String token) {
        redisTemplate.opsForValue().set(userUuid, token, 1, TimeUnit.HOURS);
    }

    @Override
    public void deleteTokenFromRedis(String userUuid) {
        redisTemplate.delete(userUuid);
    }

    @Override
    public String getTokenFromRedis(String userUuid) {
        return redisTemplate.opsForValue().get(userUuid);
    }
}
