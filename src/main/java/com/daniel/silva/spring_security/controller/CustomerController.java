package com.daniel.silva.spring_security.controller;

import com.daniel.silva.spring_security.dto.LoginRequestDTO;
import com.daniel.silva.spring_security.dto.LoginResponseDTO;
import com.daniel.silva.spring_security.model.Customer;
import com.daniel.silva.spring_security.repository.CustomerRepository;
import com.daniel.silva.spring_security.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<Customer>save(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.status(201).body(service.save(loginRequestDTO));
    }

    @PostMapping("/logi")
    public ResponseEntity<LoginResponseDTO>login(@RequestBody LoginRequestDTO loginRequest) {
      return ResponseEntity.ok().body(service.apiLogin(loginRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>>getAll(){
        return ResponseEntity.ok().body(service.getAll());

    }
















}
