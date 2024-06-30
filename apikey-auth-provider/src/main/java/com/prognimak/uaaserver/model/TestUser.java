package com.prognimak.uaaserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The test user model api object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestUser {
    private String username;
    private String password;
    private String roles;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private List<GrantedRole> authorities;
}
