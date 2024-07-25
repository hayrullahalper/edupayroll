package com.incubator.edupayroll.dto.record;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecordUpdateTeacherDTO {
  UUID teacherId;
}
