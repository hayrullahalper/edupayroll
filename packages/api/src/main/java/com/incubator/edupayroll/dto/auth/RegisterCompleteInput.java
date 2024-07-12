package com.incubator.edupayroll.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class RegisterCompleteInput {

    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    @NotEmpty(message = "First name is required")
    public String firstName;

    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    @NotEmpty(message = "Last name is required")
    public String lastName;

    @NotEmpty(message = "Token is required")
    public String token;

    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    @NotEmpty(message = "Title is required")
    public String title;

    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message =
                    "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    @NotEmpty(message = "Password is required")
    public String password;

    @Size(min = 3, max = 50, message = "School name must be between 3 and 50 characters")
    @NotEmpty(message = "School name is required")
    public String schoolName;

    @Size(min = 3, max = 50, message = "Principal name must be between 3 and 50 characters")
    @NotEmpty(message = "Principal name is required")
    public String principalName;

}
