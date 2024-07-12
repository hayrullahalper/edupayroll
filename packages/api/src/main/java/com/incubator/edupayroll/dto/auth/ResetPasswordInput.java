package com.incubator.edupayroll.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ResetPasswordInput {

    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    public String email;

}
