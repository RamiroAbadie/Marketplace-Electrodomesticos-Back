package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String surname;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private Role role;
}

