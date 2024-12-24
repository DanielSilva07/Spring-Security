package com.daniel.silva.spring_security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    @Column(name = "role")
    private String role;

    @CreationTimestamp
    private Instant creationTimestamp;

    @OneToMany(mappedBy = "customer" , fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority>authorities;


}
