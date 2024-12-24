package com.daniel.silva.spring_security.controller;

import com.daniel.silva.spring_security.model.Customer;
import com.daniel.silva.spring_security.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/customer")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return ResponseEntity.status(201).body(service.save(customer));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>>getAll(){
        return ResponseEntity.ok().body(service.getAll());

    }












}
