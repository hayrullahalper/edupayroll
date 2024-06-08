package com.incubator.edupayroll.dto.teacher;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

@Value
public class Teacher {
    @NotNull
    public UUID id;

    @NotNull
    public String name;

    @NotNull
    public String branch;

    @NotNull
    public String identityNo;
}
