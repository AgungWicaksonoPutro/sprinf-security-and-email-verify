package com.agungwicaksono.co.id.auth.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyResponse {
    private String userName;
}
