package com.nan19.roleplaying.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // 允许所有前端地址
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
        };
    }
}