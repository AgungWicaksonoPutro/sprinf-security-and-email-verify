package com.agungwicaksono.co.id.auth.controller;

import com.agungwicaksono.co.id.auth.model.request.SignUpRequest;
import com.agungwicaksono.co.id.auth.model.response.SignUpResponse;
import com.agungwicaksono.co.id.auth.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/v1/spring-security")
public class SIgnUpController {
    private SignUpService signUpService;

    public SIgnUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<SignUpResponse> signUpController(@RequestBody SignUpRequest input) throws MessagingException, UnsupportedEncodingException {
        SignUpResponse output = signUpService.execute(input);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(output);
    }
}
