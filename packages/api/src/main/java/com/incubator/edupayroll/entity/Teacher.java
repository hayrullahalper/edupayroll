package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Node {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "branch", nullable = false)
    private String branch;

    @Column(name = "id_number", nullable = false)
    private String idNumber;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}