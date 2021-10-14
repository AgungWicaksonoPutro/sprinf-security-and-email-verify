package com.agungwicaksono.co.id.auth.controller;

import com.agungwicaksono.co.id.auth.security.config.TokenManager;
import com.agungwicaksono.co.id.auth.security.implement.UserDetailsServiceImplement;
import com.agungwicaksono.co.id.auth.security.model.JwtRequestModel;
import com.agungwicaksono.co.id.auth.security.model.JwtResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/spring-security")
public class SignInController {

    private UserDetailsServiceImplement userDetailsServiceImplement;
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;

    public SignInController(UserDetailsServiceImplement userDetailsServiceImplement, AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.userDetailsServiceImplement = userDetailsServiceImplement;
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponseModel> createToken(@RequestBody JwtRequestModel input) throws Exception{
        log.info("Data Masuk di controller jwt controller");
        try {
            UsernamePasswordAuthenticationToken data = new UsernamePasswordAuthenticationToken(input.getUserName(), input.getPassword());
            log.info("data username password authen token {}", data);
            authenticationManager.authenticate(data);
            log.info("di controller authentication manager {}", authenticationManager);
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_EXCEPTIONS", e);
        }
        final UserDetails userDetails = userDetailsServiceImplement.loadUserByUsername(input.getUserName());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new JwtResponseModel(jwtToken));
    }
}
