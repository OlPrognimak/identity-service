package com.prognimak.uaaserver.controller;


import com.prognimak.uaaserver.service.UUAServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Log4j2
public class UUAServerController {
    private final UUAServerService service;

    /**
     * Creates JWT token with using secret key with username and password delimited with semicolon
     * and transferred over custom HTTP header <code>Secret</code>
     *
     * @param secret encoded String with username and password.
     * @return ResponseEntity with token
     */
    @PostMapping(path = "/createtoken", produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createToken(@RequestHeader("Secret") String secret) {
        try {
            String token = service.requestToken(secret);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error("UUAServer. Can not create JWT token", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUAServer. JWT token is not created.");
        }
    }
}
