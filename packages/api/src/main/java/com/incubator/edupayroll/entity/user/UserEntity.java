package com.incubator.edupayroll.entity.user;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @ToString.Exclude
  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "roles", nullable = false)
  @Enumerated(EnumType.STRING)
  private List<UserRole> roles;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<TeacherEntity> teachers;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<DocumentEntity> documents;
}
