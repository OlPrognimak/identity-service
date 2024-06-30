package com.prognimak.uaaserver;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${server.servlet.context-path}")
	private String contextPath;

	/**
	 * Creates rest client bean for requesting another Rest API endpoints.
	 *
	 * @return bean {@link RestClient}
	 */
	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}

//	@Bean
//	public OpenAPI usersMicroserviceOpenAPI() {
//		return new OpenAPI()
//				.addServersItem(new Server().url(contextPath))
//				.info(new Info().title("UaaServerApplication API")
//						.description("The API for test project of module uaa-server")
//						.version("1.0"));
//	}
}
