package com.agungwicaksono.co.id.auth.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyRequest {
    private String code;
}
