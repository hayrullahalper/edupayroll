package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.entity.record.RecordType;
import java.util.UUID;
import lombok.*;

@Value
public class RecordUpdateInput {
  UUID teacherId;
  RecordType type;
}
