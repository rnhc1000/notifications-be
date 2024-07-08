package com.gila.challenge.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsSecurityConfiguration implements WebMvcConfigurer {

  @Bean
  public WebMvcConfigurer corsMessageConfiguration() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/messages").allowedOrigins("http://192.168.15.11:4500");
        registry.addMapping("/logs").allowedOrigins("http://192.168.15.11:4500");
        registry.addMapping("/messages").allowedOrigins("http://127.0.0.1:4200");
        registry.addMapping("/logs").allowedOrigins("http://127.0.0.1:4200");
      }
    };
  }
}
