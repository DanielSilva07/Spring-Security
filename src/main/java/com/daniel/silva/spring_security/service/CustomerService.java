package com.daniel.silva.spring_security.service;

import com.daniel.silva.spring_security.model.Authority;
import com.daniel.silva.spring_security.model.Customer;
import com.daniel.silva.spring_security.repository.AuthorityRepository;
import com.daniel.silva.spring_security.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthorityRepository authorityRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    public Customer save(Customer customer) {

            Authority authority = new Authority();
            authority.setName("admin");
            authority.setCustomer(customer);
            authorityRepository.save(authority);
            String hashPassword = passwordEncoder.encode(customer.getPassword());
            customer.setPassword(hashPassword);
            return customerRepository.save(customer);
        }

        public List<Customer>getAll(){
            return customerRepository.findAll();
        }

    }

