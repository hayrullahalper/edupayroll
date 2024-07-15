package com.incubator.edupayroll.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UserUpdatePasswordPayload {

  @NotNull public boolean success;
}
