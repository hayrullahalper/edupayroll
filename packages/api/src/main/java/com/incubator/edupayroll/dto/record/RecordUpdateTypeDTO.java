package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.entity.record.RecordType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecordUpdateTypeDTO {
  RecordType type;
}
