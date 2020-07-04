package com.failsfast.nbastatendpoints;

import com.failsfast.nbastatendpoints.domain.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PlayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(
        @Autowired
                Headers headers) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(headers.getInterceptors());

        return restTemplate;
    }
}
