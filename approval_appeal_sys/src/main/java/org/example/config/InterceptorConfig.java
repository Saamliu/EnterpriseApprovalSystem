package org.example.config;


import org.example.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建拦截器对象
        TokenInterceptor tokenInterceptor = new TokenInterceptor();
        // 添加拦截器，并排除Swagger相关路径
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 设置拦截目录
                .excludePathPatterns("/user/login") // 设置放行目录
                .excludePathPatterns("/user/register") // 设置放行目录
                .excludePathPatterns("/static/**") // 设置放行目录
                .excludePathPatterns("/swagger-ui.html") // 排除Swagger UI路径
                .excludePathPatterns("/webjars/**") // 排除Swagger UI资源路径
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/project/export-excel")
                .excludePathPatterns("/error");
    }
}


