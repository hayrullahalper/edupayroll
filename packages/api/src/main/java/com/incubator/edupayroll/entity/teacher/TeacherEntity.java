package com.incubator.edupayroll.entity.teacher;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.persistence.*;
import java.util.List;
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

  @Column(name = "description", nullable = false)
  private String description;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ToString.Exclude
  @OneToMany
  @JoinColumn(name = "record_id")
  private List<RecordEntity> record;
}
