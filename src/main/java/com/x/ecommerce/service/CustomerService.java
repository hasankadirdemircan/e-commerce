package com.x.ecommerce.service;

import com.x.ecommerce.model.Customer;
import com.x.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        if(Objects.isNull(customer.getRoles())) {
            customer.setRoles("ROLE_USER");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }
}
