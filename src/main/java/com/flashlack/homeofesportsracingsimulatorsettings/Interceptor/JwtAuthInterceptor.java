package com.flashlack.homeofesportsracingsimulatorsettings.Interceptor;

import com.flashlack.homeofesportsracingsimulatorsettings.until.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * JWT认证拦截器
 *
 * @author FLASHLACK
 */
@Component
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

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
                             @NotNull Object handler) throws Exception {
        log.info("拦截器拦截请求");
        // 从请求头中获取 JWT 令牌
        String token = request.getHeader("Authorization");

        // 如果请求头中没有 `Authorization` 或令牌不以 `Bearer ` 开头，则返回 401 未授权
        if (token == null || !token.startsWith("Bearer ")) {
            setErrorResponse(response, "缺少令牌或令牌格式错误");
            return false;
        }

        // 去掉 "Bearer " 前缀，得到真正的 JWT 令牌
        token = token.substring(7);

        // 验证 JWT 令牌的合法性
        if (!jwtUtil.validateToken(token)) {
            setErrorResponse(response, "无效的令牌");
            return false;
        }

        // 解析令牌中的用户 UUID，并将其存储在请求属性中
        String userUuid = jwtUtil.getUserUuidFromToken(token);
        request.setAttribute("userUuid", userUuid);

        // 如果令牌验证通过，则放行请求
        return true;
    }

    /**
     * 设置 HTTP 错误响应
     *
     * @param response HTTP 响应对象
     * @param message  错误消息
     */
    private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应内容类型为 JSON 格式
        response.setContentType("application/json;charset=UTF-8");
        // 设置响应字符编码
        response.setCharacterEncoding("UTF-8");
        // 构建 JSON 格式的响应体
        String jsonResponse = String.format("{\"status\": %d, \"error\": \"%s\"}", HttpServletResponse.SC_UNAUTHORIZED, message);
        // 写入响应内容
        response.getWriter().write(jsonResponse);
    }
}