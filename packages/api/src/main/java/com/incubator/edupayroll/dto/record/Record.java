package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.dto.teacher.Teacher;
import com.incubator.edupayroll.entity.record.RecordType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Value;

@Value
public class Record {
  @NotNull public UUID id;

  public UUID nextId;
  @NotNull public RecordType type;
  @NotNull public Teacher teacher;
  @NotNull public List<Integer> hours;

  @NotNull public LocalDateTime createdAt;
  @NotNull public LocalDateTime updatedAt;
}
