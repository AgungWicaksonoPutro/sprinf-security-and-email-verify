package com.agungwicaksono.co.id.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/spring-security")
public class ClientController {

    @GetMapping("/tes")
    public String testing(){
        log.info("Masuk di controller client controller");
        return "hello world";
    }
}
