package com.incubator.edupayroll.entity.record;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "record")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecordEntity extends BaseEntity {
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private RecordType type;

  @Column(name = "information", nullable = false)
  private String information;

  @Column(name = "head", nullable = false)
  private boolean head;

  @Column(name = "next_id")
  private UUID nextId;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private TeacherEntity teacher;

  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "document_id", nullable = false)
  private DocumentEntity document;
}
