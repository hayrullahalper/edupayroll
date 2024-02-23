package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"passwordHash","createdAt","updatedAt"})
@Entity
@Table(name = "User")
public class User extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

}