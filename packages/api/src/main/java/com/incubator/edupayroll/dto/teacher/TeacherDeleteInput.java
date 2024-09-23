package com.incubator.edupayroll.dto.teacher;

import com.incubator.edupayroll.common.selection.SelectionType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Value;

@Value
public class TeacherDeleteInput {

  @NotEmpty(message = "Teacher's ID is required")
  public List<@NotBlank String> ids;

  @Nullable public SelectionType type;
}
