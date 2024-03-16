package com.incubator.edupayroll.dto.teacher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TeacherCreateDTO {
    String name;
    String branch;

    @JsonProperty("id_number")
    String idNumber;
}
