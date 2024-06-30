package com.prognimak.uaaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Entry point of spring boot application.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@Configuration
public class UaaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaaServerApplication.class, args);
	}

	/**
	 * Creates rest client bean for requesting another Rest API endpoints.
	 *
	 * @return bean {@link RestClient}
	 */
	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}
}
