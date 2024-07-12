package com.incubator.edupayroll.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserChangePasswordInput {

    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message =
                    "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    @NotEmpty(message = "Current password is required")
    public String currentPassword;

    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message =
                    "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    @NotEmpty(message = "New password is required")
    public String newPassword;

}
