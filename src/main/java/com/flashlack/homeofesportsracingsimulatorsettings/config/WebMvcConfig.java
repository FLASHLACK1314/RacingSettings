package com.flashlack.homeofesportsracingsimulatorsettings.config;

import com.flashlack.homeofesportsracingsimulatorsettings.Interceptor.JwtAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 *
 * @author FLASHLACK
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                // 拦截 "/api/**" 路径下的所有请求
                .addPathPatterns("v1/user/**")
                // 放行 "/auth/**" 和 "/login" 路径
                .excludePathPatterns( "/v1/auth/**","/v3/mail/**", "v2/**","/v1/initializer/**");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许访问的路径
        registry.addMapping("/api/**")
                // 允许的前端地址
                .allowedOrigins("http://127.0.0.1:5501")
                // 允许的HTTP方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 允许的请求头
                .allowedHeaders("*")
                // 是否允许发送Cookie
                .allowCredentials(true);
    }
}
