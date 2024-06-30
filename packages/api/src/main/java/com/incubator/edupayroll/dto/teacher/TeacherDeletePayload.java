package com.incubator.edupayroll.dto.teacher;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class TeacherDeletePayload {
    @NotNull
    public boolean success;
}
