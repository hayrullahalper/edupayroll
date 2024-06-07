package com.incubator.edupayroll.dto.school;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class SchoolUpdateDTO {
    @Size(min = 3, max = 50, message = "School name must be between 3 and 50 characters")
    public String name;

    @Size(min = 3, max = 50, message = "Editor name must be between 3 and 50 characters")
    public String editorName;

    @Size(min = 3, max = 50, message = "Editor title must be between 3 and 50 characters")
    public String editorTitle;

    @Size(min = 3, max = 50, message = "Principal name must be between 3 and 50 characters")
    public String principalName;
}
