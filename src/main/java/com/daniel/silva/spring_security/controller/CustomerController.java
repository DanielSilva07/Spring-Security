package com.daniel.silva.spring_security.controller;

import com.daniel.silva.spring_security.model.Customer;
import com.daniel.silva.spring_security.repository.CustomerRepository;
import com.daniel.silva.spring_security.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final CustomerRepository repository;

    @PostMapping("/customer")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return ResponseEntity.status(201).body(service.save(customer));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>>getAll(){
        return ResponseEntity.ok().body(service.getAll());

    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = repository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }












}
