package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "document_row")
public class DocumentRow extends Node {

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher teacher;

    @Column(name = "course_type", nullable = false)
    private CourseType courseType;

    @Column(name = "information", nullable = false)
    private String information;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;

    @Column(name = "index", nullable = false)
    private int index;

}