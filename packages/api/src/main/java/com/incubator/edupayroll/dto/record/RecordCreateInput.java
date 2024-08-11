package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.entity.record.RecordType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Value
public class RecordCreateInput {
  UUID previousId;

  @NotNull(message = "Document ID is required")
  UUID documentId;

  @NotNull(message = "Teacher ID is required")
  UUID teacherId;

  @NotNull(message = "Record type is required")
  RecordType type;

  @NotEmpty(message = "Hours are required")
  List<Integer> hours;
}
