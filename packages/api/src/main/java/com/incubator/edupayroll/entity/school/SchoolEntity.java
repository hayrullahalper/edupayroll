package com.incubator.edupayroll.entity.school;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "school")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SchoolEntity extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "editor_name", nullable = false)
  private String editorName;

  @Column(name = "editor_title", nullable = false)
  private String editorTitle;

  @Column(name = "principal_name", nullable = false)
  private String principalName;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
}
