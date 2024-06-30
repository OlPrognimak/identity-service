package com.prognimak.uaaserver.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.prognimak.UUATestConfiguration;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8083)
@Import({UUATestConfiguration.class})
@Log4j2
class UUAServerControllerTest {

    @Autowired
    private WireMockServer wireMockServer;

    private static final String TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0ZXN0VXNlciIsInN1YiI6InRlc3RVc2VyIiwiaWF0IjoxNzE5NjExMjIzLCJleHAiOjE3MTk2MTE4MjMsInJvbGUiOiJBZG1pbiJ9.Dzbs9lyUO_VIMoTwlbe142u1S8VzpUh7-i74Gm104P8";

    @Autowired
    private RestTemplate testClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createToken() {
        final String testUrl = "/uaaserver/createtoken";
        final String expectedBody = """
                 {"username":"testUserName","password":"testPassword",
                 "roles":null,"accountNonExpired":true,"accountNonLocked":true
                 ,"credentialsNonExpired":true,"enabled":false,"authorities":[{"authority":"ROLE_USER"}]}
                """;

        final String userNamePassword = "testUserName:testPassword";
        final String encodePassword = Base64.getEncoder().encodeToString(userNamePassword.getBytes());
        log.info("Creates encoded user name and password: {}", encodePassword );

        wireMockServer.stubFor(WireMock.post(urlEqualTo("/tokenserver/createtoken"))
                        .withRequestBody( equalToJson(expectedBody))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "plain/text")
                        .withBody(TEST_TOKEN)
                ));

        RequestEntity request = RequestEntity
                .post("http://localhost:" + port + "/uaaserver/createtoken")
                .header("Content-Type", "text/plain")
                .header("Secret", encodePassword)
                .accept(MediaType.TEXT_PLAIN).build();
        ResponseEntity<String> exchange = testClient.exchange(request, String.class);
        assertEquals(200, exchange.getStatusCode().value());
        assertNotNull(exchange.getBody());
    }


}