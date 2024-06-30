package com.prognimak.uaaserver.service;

import com.prognimak.uaaserver.ApikeyAuthTestConfiguration;
import com.prognimak.uaaserver.model.TestUser;
import com.prognimak.uaaserver.model.GrantedRole;
import com.prognimak.uaaserver.security.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({ApiKeyAuthProviderService.class, ApikeyAuthTestConfiguration.class, JwtUtil.class})
class ApiKeyAuthProviderServiceTest {

    @Autowired
    private ApiKeyAuthProviderService authProviderService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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

        String token = authProviderService.createToken(testUser);
        System.out.println(token);
        assertNotNull(token);
        assertEquals(testUser.getUsername(), JwtUtil.extractUsername(token, authProviderService.getAuthKey()));
    }
}