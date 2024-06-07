package com.incubator.edupayroll.dto.school;

import lombok.Value;

import java.util.UUID;

@Value
public class SchoolDTO {
    public UUID id;
    public String name;
    public String editorName;
    public String editorTitle;
    public String principalName;
}
