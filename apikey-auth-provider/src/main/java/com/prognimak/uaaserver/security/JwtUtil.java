package com.prognimak.uaaserver.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * The utility class for management with JWT token
 */
public final class JwtUtil {

    private JwtUtil() {

    }

    /**
     * Extracts username from token
     *
     * @param token JWT token
     * @param authKey authority key
     * @return the username extracted from token
     */
   public static String extractUsername(String token, String authKey) {
       Algorithm algorithm = Algorithm.HMAC256(authKey);
       JWTVerifier verifier = JWT.require(algorithm).build();
       DecodedJWT jwt = verifier.verify(token);
       return jwt.getSubject();
   }
}
