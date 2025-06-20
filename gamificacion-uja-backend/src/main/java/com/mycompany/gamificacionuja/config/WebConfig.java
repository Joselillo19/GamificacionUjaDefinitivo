package com.mycompany.gamificacionuja.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Esta clase es por el CrossOrigin y no de problemas en el front
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
          .addMapping("/**") 
          .allowedOrigins("http://localhost:3000")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowCredentials(true);
    }
}
