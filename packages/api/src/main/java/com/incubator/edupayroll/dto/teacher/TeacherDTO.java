package com.incubator.edupayroll.dto.teacher;

import lombok.Value;

import java.util.UUID;

@Value
public class TeacherDTO {
    public UUID id;
    public String name;
    public String branch;
    public String identityNo;
}
