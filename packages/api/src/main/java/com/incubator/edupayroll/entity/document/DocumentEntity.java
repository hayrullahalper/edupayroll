package com.incubator.edupayroll.entity.document;

import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.export.ExportEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import jakarta.persistence.*;
import java.time.YearMonth;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "document")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "time", nullable = false)
  private YearMonth time;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ToString.Exclude
  @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
  private List<ExportEntity> exports;

  @ToString.Exclude
  @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
  private List<RecordEntity> records;
}
