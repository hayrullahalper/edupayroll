package com.incubator.edupayroll.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "DocumentRow")
public class DocumentRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    // CourseType enum lazıms
    @Column(name = "course_type")
    private String courseType;

    @Column(name = "information")
    private String information;


    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;
}
