package com.incubator.edupayroll.dto.teacher;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TeacherUpdateInput {

  @Size(min = 3, max = 50, message = "Teacher's first name must be between 3 and 50 characters")
  public String firstName;

  @Size(min = 3, max = 50, message = "Teacher's last name must be between 3 and 50 characters")
  public String lastName;

  @Size(min = 3, max = 50, message = "Branch name must be between 3 and 50 characters")
  public String branch;

  @Size(min = 11, max = 11, message = "ID number must be 11 characters")
  public String identityNo;
}
