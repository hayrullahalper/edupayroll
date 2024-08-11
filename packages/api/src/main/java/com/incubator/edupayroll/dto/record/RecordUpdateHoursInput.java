package com.incubator.edupayroll.dto.record;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.*;

@Getter
public class RecordUpdateHoursInput {
  @NotEmpty(message = "Hours are required")
  List<Integer> hours;
}
