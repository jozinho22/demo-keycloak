package com.douineau.demokeycloak.security;

import lombok.Data;

@Data
public class JwtAuthenticationRequest {

    private String username;
    private String password;
}
