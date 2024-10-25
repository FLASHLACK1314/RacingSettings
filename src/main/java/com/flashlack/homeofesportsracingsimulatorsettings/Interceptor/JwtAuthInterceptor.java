package com.flashlack.homeofesportsracingsimulatorsettings.Interceptor;

import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.JwtUtil;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT认证拦截器
 *
 * @author FLASHLACK
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    /**
     * 请求前拦截，验证 JWT 令牌的合法性。
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @param handler  处理器对象
     * @return 如果令牌验证通过，返回 true，否则返回 false
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        log.info("拦截器拦截请求");
        // 从请求头中获取 JWT 令牌
        String token = request.getHeader("Authorization");

        // 如果请求头中没有 `Authorization` 或令牌不以 `Bearer ` 开头，则返回 401 未授权
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BusinessException("无效的令牌", ErrorCode.HEADER_ERROR);
        }
        // 去掉 "Bearer " 前缀，得到真正的 JWT 令牌
        token = token.substring(7);
        // 验证 JWT 令牌的合法性
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException("无效的令牌", ErrorCode.HEADER_ERROR);
        }
        // 解析令牌中的用户 UUID，并将其存储在请求属性中
        String userUuid = jwtUtil.getUserUuidFromToken(token);
        String redisToken = redisService.getTokenFromRedis(userUuid);
        if (redisToken == null || !redisToken.equals(token)) {
            throw new BusinessException("令牌与服务器不匹配，请重新登录", ErrorCode.HEADER_ERROR);
        }

        request.setAttribute("userUuid", userUuid);
        // 如果令牌验证通过，则放行请求
        return true;
    }

}