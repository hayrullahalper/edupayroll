package com.incubator.edupayroll.dto.school;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.UUID;

@Value
public class School {
    @NotNull
    public UUID id;

    @NotNull
    public String name;

    @NotNull
    public String editorName;

    @NotNull
    public String editorTitle;

    @NotNull
    public String principalName;
}
