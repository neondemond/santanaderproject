package com.santander.mx.security.model;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

}
