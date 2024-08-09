package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.dto.teacher.Teacher;
import com.incubator.edupayroll.entity.record.RecordType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Value;

@Value
public class Record {
  public UUID nextId;

  @NotNull public RecordType type;

  @NotNull public String information;

  @NotNull public Teacher teacher;
}
