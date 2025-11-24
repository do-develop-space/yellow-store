package com.example.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(clientHttpRequestFactory());
//        return restTemplate;
//    }

//    @Bean
//    public ClientHttpRequestFactory clientHttpRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(5000); // 5초
//        factory.setReadTimeout(5000); // 5초
//        return factory;
//    }
}

