package com.incubator.edupayroll.dto.teacher;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Value;

@Value
public class Teacher {
  @NotNull public UUID id;

  @NotNull public String name;
  @NotNull public String branch;
  @NotNull public String idNumber;
  @NotNull public String description;

  @NotNull public LocalDateTime createdAt;
  @NotNull public LocalDateTime updatedAt;
}
