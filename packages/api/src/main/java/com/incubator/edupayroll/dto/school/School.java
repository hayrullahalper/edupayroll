package com.incubator.edupayroll.dto.school;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Value;

@Value
public class School {
  @NotNull public UUID id;

  @NotNull public String name;

  @NotNull public String editorName;

  @NotNull public String editorTitle;

  @NotNull public String principalName;
}
