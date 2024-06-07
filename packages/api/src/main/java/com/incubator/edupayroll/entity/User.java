package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class User extends Node {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Teacher> teachers;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Document> documents;
}