package com.incubator.edupayroll.dto.teacher;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TeacherCreateInput {

  @Size(min = 3, max = 50, message = "Teacher's name must be between 3 and 50 characters")
  @NotEmpty(message = "Teacher's name is required")
  public String name;

  @Size(min = 3, max = 50, message = "Branch name must be between 3 and 50 characters")
  @NotEmpty(message = "Branch name is required")
  public String branch;

  @Size(min = 11, max = 11, message = "ID number must be 11 characters")
  @NotEmpty(message = "ID number is required")
  public String idNumber;
}
