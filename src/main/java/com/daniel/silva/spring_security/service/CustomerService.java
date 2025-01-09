package com.daniel.silva.spring_security.service;

import com.daniel.silva.spring_security.constants.ApplicationConstants;
import com.daniel.silva.spring_security.dto.LoginRequestDTO;
import com.daniel.silva.spring_security.dto.LoginResponseDTO;
import com.daniel.silva.spring_security.model.Authority;
import com.daniel.silva.spring_security.model.Customer;
import com.daniel.silva.spring_security.repository.AuthorityRepository;
import com.daniel.silva.spring_security.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthorityRepository authorityRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    public Customer save(LoginRequestDTO loginRequest) {

        Customer customer = new Customer();
        customer.setPassword(loginRequest.password());
        customer.setEmail(loginRequest.username());
        customer.setRole(loginRequest.role());
        Authority authority = new Authority();
        authority.setName(loginRequest.role());
        authority.setCustomer(customer);
        authorityRepository.save(authority);
        String hashPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashPassword);
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }


    public LoginResponseDTO apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String jwt = "";
            if (loginRequest != null && loginRequest.username() != null && loginRequest.password() != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.username(),
                        loginRequest.password());
                Authentication authenticationResponse = authenticationManager.authenticate(authentication);
                if (authenticationResponse != null && authenticationResponse.isAuthenticated() && env != null) {
                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                            ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    jwt = Jwts.builder().issuer("Daniel Dev").subject("JWT Token")
                            .claim("username", authenticationResponse.getName())
                            .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                    GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                            .issuedAt(new java.util.Date())
                            .expiration(new java.util.Date((new java.util.Date()).getTime() + 30000000))
                            .signWith(secretKey).compact();
                }
            }
            return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, jwt)
                    .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt)).getBody();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(HttpStatus.UNAUTHORIZED.getReasonPhrase(), null)).getBody();
        }
    }


}

