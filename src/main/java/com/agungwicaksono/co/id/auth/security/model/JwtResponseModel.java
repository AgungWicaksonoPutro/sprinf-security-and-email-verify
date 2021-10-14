package com.agungwicaksono.co.id.auth.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponseModel implements Serializable {
    private final static long serialVersionUUID = 1L;
    private final String token;

    public JwtResponseModel(String token) {
        this.token = token;
    }
}
