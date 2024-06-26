package com.incubator.edupayroll.dto.teacher;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TeacherUpdateInput {

    @Size(min = 3, max = 50, message = "Teacher name must be between 3 and 50 characters")
    public String name;

    @Size(min = 3, max = 50, message = "Branch name must be between 3 and 50 characters")
    public String branch;

    @Size(min = 3, max = 50, message = "ID number must be between 3 and 50 characters")
    public String identityNo;

}
