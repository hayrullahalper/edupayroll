package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.*;

enum CourseType {
    GUNDUZ(101),
    GECE(102);

    private int code;

    private CourseType(int code) {
        this.code = code;
    }
}

@Entity
@Table(name = "document_row")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRow extends Node {

    @Column(name = "line", nullable = false)
    private int line;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", nullable = false)
    private CourseType courseType;

    @Column(name = "information", nullable = false)
    private String information;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}