package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "record")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record extends Node {

    @Column(name = "line", nullable = false)
    private int line;

    @Enumerated(EnumType.STRING)
    @Column(name = "course", nullable = false)
    private Course course;

    @Column(name = "information", nullable = false)
    private String information;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

}