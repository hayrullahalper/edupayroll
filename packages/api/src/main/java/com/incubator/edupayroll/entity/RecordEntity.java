package com.incubator.edupayroll.entity;

import com.incubator.edupayroll.utils.CourseType;
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
public class DocumentRowEntity extends BaseEntity {

    @Column(name = "line", nullable = false)
    private int line;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", nullable = false)
    private CourseType courseType;

    @Column(name = "information", nullable = false)
    private String information;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentEntity document;

}