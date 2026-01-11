package com.server.social_network_server.config;

import org.jspecify.annotations.NonNull;
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
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") // תופס את כל הפונקציות באתר
                        .allowedOriginPatterns("*") // הפתרון החכם: מאפשר כוכבית ביחד עם Credentials
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // כל סוגי הבקשות
                        .allowedHeaders("*") // מאשר את ה-Ngrok Header ושאר הדברים
                        .allowCredentials(true); // מאפשר עוגיות ואותנטיקציה
            }
        };
    }
}
