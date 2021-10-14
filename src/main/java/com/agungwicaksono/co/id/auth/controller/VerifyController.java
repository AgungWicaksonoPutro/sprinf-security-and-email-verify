package com.agungwicaksono.co.id.auth.controller;

import com.agungwicaksono.co.id.auth.model.request.VerifyRequest;
import com.agungwicaksono.co.id.auth.model.response.VerifyResponse;
import com.agungwicaksono.co.id.auth.service.VerifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/spring-security")
public class VerifyController {

    private VerifyService verifyService;

    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @GetMapping("/auth/verify")
    public VerifyResponse verifyEmail(@RequestParam(name = "code") String code){
        System.out.println(code);
        VerifyRequest input = VerifyRequest.builder().code(code).build();
        return verifyService.execute(input);
    }
}
