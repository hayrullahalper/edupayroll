package com.incubator.edupayroll.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserNameUpdateInput {

    @Size(min = 3, max = 50, message = "Teacher's first name must be between 3 and 50 characters")
    public String firstName;

    @Size(min = 3, max = 50, message = "Teacher's last name must be between 3 and 50 characters")
    public String lastName;

}
