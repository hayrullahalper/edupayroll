package com.incubator.edupayroll.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UserUpdatePayload {

  @NotNull public boolean success;
}
