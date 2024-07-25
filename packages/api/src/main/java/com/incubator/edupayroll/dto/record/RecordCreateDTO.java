package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.entity.record.RecordType;
import java.util.UUID;
import lombok.Value;

@Value
public class RecordCreateDTO {

  RecordType type;
  String information;
  UUID teacher;
}
