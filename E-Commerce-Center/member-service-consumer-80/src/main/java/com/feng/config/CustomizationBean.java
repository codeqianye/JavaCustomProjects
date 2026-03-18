package com.feng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomizationBean {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}