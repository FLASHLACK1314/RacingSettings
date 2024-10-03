package com.flashlack.homeofesportsracingsimulatorsettings.config;

import com.flashlack.homeofesportsracingsimulatorsettings.Interceptor.JwtAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 *
 * @author FLASHLACK
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                // 拦截 "/api/**" 路径下的所有请求
                .addPathPatterns("v1/user/**")
                // 放行 "/auth/**" 和 "/login" 路径
                .excludePathPatterns("v1/auth/**","v3/mail/**","v2/**");
    }
}
