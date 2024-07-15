package com.incubator.edupayroll.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ResetPasswordPayload {

  @NotNull public boolean success;
}
