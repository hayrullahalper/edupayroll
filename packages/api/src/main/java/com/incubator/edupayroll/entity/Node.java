package com.incubator.edupayroll.entity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@MappedSuperclass
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private LocalTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalTime updatedAt;

}
