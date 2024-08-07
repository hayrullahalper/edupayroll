package com.incubator.edupayroll.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class UserUpdateEmailInput {

  @Email(message = "Invalid email")
  @NotEmpty(message = "Email is required")
  public String email;

  @NotEmpty(message = "Password is required")
  public String password;
}
