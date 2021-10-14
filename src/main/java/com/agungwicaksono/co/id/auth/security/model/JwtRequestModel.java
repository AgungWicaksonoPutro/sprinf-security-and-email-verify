package com.agungwicaksono.co.id.auth.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequestModel implements Serializable {
    private String userName;
    private String password;
    private String email;

    public JwtRequestModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
