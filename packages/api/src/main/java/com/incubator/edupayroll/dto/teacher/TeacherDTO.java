package com.incubator.edupayroll.dto.teacher;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.UUID;

@Value
public class TeacherDTO {
    UUID id;
    String name;
    String branch;

    @JsonProperty("id_number")
    String idNumber;
}
