package com.agungwicaksono.co.id.auth.service;

import com.agungwicaksono.co.id.auth.model.entity.Customer;
import com.agungwicaksono.co.id.auth.model.request.SignUpRequest;
import com.agungwicaksono.co.id.auth.model.response.SignUpResponse;
import com.agungwicaksono.co.id.auth.repository.CustomerRepository;
import com.agungwicaksono.co.id.auth.security.config.WebSecurityConfig;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class SignUpService {

    private CustomerRepository customerRepository;
    private WebSecurityConfig webSecurityConfig;
    private SendVerificationEmailService emailService;

    public SignUpService(CustomerRepository customerRepository, WebSecurityConfig webSecurityConfig, SendVerificationEmailService emailService) {
        this.customerRepository = customerRepository;
        this.webSecurityConfig = webSecurityConfig;
        this.emailService = emailService;
    }

    public SignUpResponse execute(SignUpRequest input) throws MessagingException, UnsupportedEncodingException {
        String randomCode = RandomString.make(64);

        Customer customer = Customer.builder()
                .userName(input.getUserName())
                .isEnable(false)
                .email(input.getEmail())
                .verificationCode(randomCode)
                .password(webSecurityConfig.passwordEncoder().encode(input.getPassword()))
                .build();

        Customer output = customerRepository.save(customer);

        emailService.execute(output, "http://localhost:5800/v1/spring-security/auth");

        return SignUpResponse.builder().userName(output.getUserName()).build();
    }
}
