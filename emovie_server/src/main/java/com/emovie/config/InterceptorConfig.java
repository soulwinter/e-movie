package com.emovie.config;


import com.emovie.config.interceptor.JWTInterceptors;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptors())
                .addPathPatterns("/**")  // 其他接口token验证
                .excludePathPatterns("/user/**")  // 所有用户都放行
                .excludePathPatterns("/movie/**");// 所有电影都放行
    }
}
