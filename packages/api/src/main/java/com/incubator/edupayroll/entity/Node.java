package com.incubator.edupayroll.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@MappedSuperclass
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}
