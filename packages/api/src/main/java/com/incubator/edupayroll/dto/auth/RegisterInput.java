package com.incubator.edupayroll.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegisterInput {

    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    public String email;

}
