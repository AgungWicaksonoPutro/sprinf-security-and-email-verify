package com.agungwicaksono.co.id.auth.security.implement;

import com.agungwicaksono.co.id.auth.model.entity.Customer;
import com.agungwicaksono.co.id.auth.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class UserDetailsServiceImplement implements UserDetailsService {

    private CustomerRepository customerRepository;

    public UserDetailsServiceImplement(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer output = customerRepository.findByUserName(s);
        return UserDetailsImplement.build(output);
    }
}
