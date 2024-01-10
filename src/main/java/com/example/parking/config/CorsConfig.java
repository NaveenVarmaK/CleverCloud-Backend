package com.example.parking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow CORS on all endpoints
                .allowedOrigins("*")  // Allow all origins, or specify your allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specify your allowed HTTP methods
                .allowedHeaders("*");  // Allow all headers, or specify your allowed headers
    }
}
