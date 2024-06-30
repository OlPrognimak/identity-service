package com.prognimak.uaaserver;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The test configuration for spring boot test.
 */
@TestConfiguration
public class ApikeyAuthTestConfiguration {

    /**
     * Creates rest client bean for testing rest service.
     *
     * @return instance of rest client bean
     */
    @Bean
    public RestTemplate restClient() {
        return new RestTemplate();
    }
}
