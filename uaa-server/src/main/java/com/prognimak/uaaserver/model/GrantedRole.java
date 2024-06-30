package com.prognimak.uaaserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The model object which contains granted role for user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantedRole {
    private String authority;
}
