package com.gila.challenge.notification.config;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Approach: WebMvcConfigurer Bean
 */
@Configuration
public class CorsSecurityConfiguration implements WebMvcConfigurer {
  @Bean
  public WebMvcConfigurer corsMessageConfiguration() {

    return new WebMvcConfigurer() {

      @Override
      public void addCorsMappings(@Nonnull CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins(
                "http://192.168.0.12:9000",
                "http://127.0.0.1:9000",
                "http://localhost:9000",
                "http://localhost"
            )
            .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
            .allowedHeaders("*");
      }
    };
  }
}
