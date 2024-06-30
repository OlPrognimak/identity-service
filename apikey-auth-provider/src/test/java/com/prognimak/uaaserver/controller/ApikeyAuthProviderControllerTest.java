package com.prognimak.uaaserver.controller;

import com.prognimak.uaaserver.ApikeyAuthTestConfiguration;
import com.prognimak.uaaserver.model.TestUser;
import com.prognimak.uaaserver.model.GrantedRole;
import com.prognimak.uaaserver.security.JwtUtil;
import com.prognimak.uaaserver.service.ApiKeyAuthProviderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ApikeyAuthProviderController.class,
        ApikeyAuthTestConfiguration.class})
class ApikeyAuthProviderControllerTest {
    private static  final Logger logger = LogManager.getLogger(ApikeyAuthProviderControllerTest.class);

    @Autowired
    private RestTemplate restClient;

    @Autowired
    private ApiKeyAuthProviderService authProviderService;

    @LocalServerPort
    private int port;

    @Test
    void createToken() {
        TestUser testUser = TestUser.builder()
                .accountNonExpired(true)
                .accountNonLocked(true)
                .username("testUser")
                .password(BCrypt.hashpw("TestPassword",  BCrypt.gensalt(10)))
                .credentialsNonExpired(true)
                .authorities(List.of(new GrantedRole("Admin")))
                .build();

        RequestEntity<TestUser> request = RequestEntity
                .post("http://localhost:" + port + "/tokenserver/api/v1/createtoken")
                .header("Content-Type", "application/json")
                .accept(MediaType.TEXT_PLAIN).body(testUser);

        ResponseEntity<String> exchange = restClient.exchange(request, String.class);
        assertEquals(200, exchange.getStatusCode().value());
        logger.info(exchange.getBody());
        assertEquals(JwtUtil.extractUsername(exchange.getBody(), authProviderService.getAuthKey()), testUser.getUsername());


    }
}