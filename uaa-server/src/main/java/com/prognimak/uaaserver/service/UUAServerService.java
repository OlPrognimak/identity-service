package com.prognimak.uaaserver.service;

import com.prognimak.uaaserver.model.GrantedRole;
import com.prognimak.uaaserver.model.TestUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.List;

/**
 * This service manage with JWT token.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UUAServerService {

    @Value("${app.server.token-server}")
    private String tokenServerUrl;

    private final RestClient restClient;

    /**
     * Requests JWT token from apikey provider service.
     *
     * @param secret encoded string with username and password
     *
     * @return JWT token
     */
    public String requestToken(String secret) {
        log.debug("Start requesting token.");
        String decodedSecret = new String(Base64.getDecoder().decode(secret));
        String[] parts = decodedSecret.split(":");
        String userName = parts[0];
        String password = parts[1];

        TestUser testUser = TestUser.builder()
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .username(userName)
                .password(password)
                .authorities(List.of(new GrantedRole("ROLE_USER")))
                .build();

        return restClient.post()
                .uri(tokenServerUrl)
                .body(testUser)
                .retrieve()
                .body(String.class);

    }
}
