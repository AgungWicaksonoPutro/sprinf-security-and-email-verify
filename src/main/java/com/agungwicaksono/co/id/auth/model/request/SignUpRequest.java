package com.agungwicaksono.co.id.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SignUpRequest {
    private String userName;
    private String password;
    private String email;
}
