package com.incubator.edupayroll.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

@Value
public class User {
    @NotNull
    public UUID id;

    @NotNull
    public String name;

    @NotNull
    public String email;
}
