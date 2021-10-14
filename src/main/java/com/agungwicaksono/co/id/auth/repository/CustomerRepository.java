package com.agungwicaksono.co.id.auth.repository;

import com.agungwicaksono.co.id.auth.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByUserName(String userName);
    Customer findByVerificationCode(String code);
}
