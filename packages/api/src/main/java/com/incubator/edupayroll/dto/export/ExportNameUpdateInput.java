package com.incubator.edupayroll.dto.export;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ExportNameUpdateInput {
  @Size(min = 3, max = 50, message = "Export's name must be between 3 and 50 characters")
  @NotEmpty(message = "Export's name is required")
  public String name;
}
