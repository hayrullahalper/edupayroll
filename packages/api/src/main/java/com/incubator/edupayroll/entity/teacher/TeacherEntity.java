package com.incubator.edupayroll.entity.teacher;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeacherEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "branch", nullable = false)
    private String branch;

    @Column(name = "id_number", nullable = false)
    private String idNumber;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}