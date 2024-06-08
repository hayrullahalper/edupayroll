package com.incubator.edupayroll.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class TokenPayload {
    @NotNull
    public String token;
}
