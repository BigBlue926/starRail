package com.example.starrail.config;

import com.example.starrail.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //这个不能放在处理跨域后面
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate)).addPathPatterns("/**").excludePathPatterns("/login");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
                //  允许访问的路径
        registry.addMapping("/**").
                allowedOriginPatterns("*").
//                allowedOrigins("http://localhost:7000").
                allowedMethods("*").
                allowCredentials(true).
                maxAge(36000).allowedHeaders("*").exposedHeaders("*");
    }



}
