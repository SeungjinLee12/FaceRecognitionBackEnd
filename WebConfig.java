package com.board;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 첫 번째 도메인에 대한 CORS 설정
        registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
    }
}

