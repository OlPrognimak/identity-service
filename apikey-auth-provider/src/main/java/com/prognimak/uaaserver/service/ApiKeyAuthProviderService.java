package com.prognimak.uaaserver.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.prognimak.uaaserver.model.TestUser;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * The service for management with JWT token.
 */
@Service
@Log4j2
public class ApiKeyAuthProviderService {

    private static final int MINUTE = 60 * 1000;
    /** Secret auth key for JWT token.*/
    @Getter
    @Value("${app.apikeyauth.security.auth-key:auth-key}")
    private String authKey;
    /** The period in minutes when token will be valid. */
    @Value("${app.apikeyauth.security.valid-minutes:10}")
    private int validPeriod;


    @PostConstruct
    protected void init() {
        authKey = Base64.getEncoder().encodeToString(authKey.getBytes());
    }

    /**
     *
     * @param user the user
     * @return the JWT tocken
     */
    public String createToken(TestUser user) {
        Date currDate = new Date();
        Date validUntill = new Date(currDate.getTime() + validPeriod * MINUTE );
        String tocken = JWT.create()
                .withIssuer(user.getUsername())
                .withSubject(user.getUsername())
                .withIssuedAt(currDate)
                .withExpiresAt(validUntill)
                .withClaim("role", user.getAuthorities().stream().map(
                        r -> r.getAuthority()).collect(Collectors.joining(",")))
                .sign(Algorithm.HMAC256(authKey));

        log.info("JWT Service creates token {} ",  tocken);
        return tocken;
    }

}
