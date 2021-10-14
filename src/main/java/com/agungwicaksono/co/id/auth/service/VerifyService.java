package com.agungwicaksono.co.id.auth.service;

import com.agungwicaksono.co.id.auth.model.entity.Customer;
import com.agungwicaksono.co.id.auth.model.request.VerifyRequest;
import com.agungwicaksono.co.id.auth.model.response.VerifyResponse;
import com.agungwicaksono.co.id.auth.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class VerifyService {

    private CustomerRepository customerRepository;

    public VerifyService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public VerifyResponse execute(VerifyRequest input){
        Customer customer = customerRepository.findByVerificationCode(input.getCode());

        customer.setIsEnable(true);
        Customer output = customerRepository.save(customer);

        return VerifyResponse.builder().userName(output.getUserName()).build();
    }
}
