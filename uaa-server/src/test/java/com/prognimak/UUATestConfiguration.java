package com.prognimak;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class UUATestConfiguration {

    @Bean
    public RestTemplate testClient() {
        return new RestTemplate();
    }

}
