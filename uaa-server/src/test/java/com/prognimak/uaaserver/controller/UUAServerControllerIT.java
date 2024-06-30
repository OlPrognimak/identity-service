package com.prognimak.uaaserver.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.prognimak.UUATestConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({UUATestConfiguration.class})
class UUAServerControllerIT {
    @Autowired
    private RestTemplate testClient;

    @LocalServerPort
    private int port;


    @Test
    public void createToken() {
        final String userNamePassword = "testUserName:testPassword";

        final String encodeUserPassword =
                Base64.getEncoder().encodeToString(userNamePassword.getBytes());

        RequestEntity request = RequestEntity
                .post("http://localhost:" + port + "/uaaserver/createtoken")
                .header("Content-Type", "text/plain")
                .header("Secret", encodeUserPassword)
                .accept(MediaType.TEXT_PLAIN).build();
        ResponseEntity<String> exchange = testClient.exchange(request, String.class);
        assertEquals(200, exchange.getStatusCode().value());
        assertEquals(209, exchange.getBody().length());
    }


}