package com.prognimak.uaaserver.controller;

import com.prognimak.uaaserver.model.TestUser;
import com.prognimak.uaaserver.service.ApiKeyAuthProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The rest controller which provides end points for management with JWT token.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Log4j2
public class ApikeyAuthProviderController {
    private final ApiKeyAuthProviderService service;

    /**
     * Creates and retrieves JWT token.
     *
     * @param user the user object which will be used for creation JWT token.
     *
     * @return ResponseEntity with JWT token
     */
    @PostMapping(path = "/createtoken", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createToken(@RequestBody TestUser user) {
        try {
            String token = service.createToken(user);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error("Can not create JWT token", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JWT token is not created.");
        }
    }
}
