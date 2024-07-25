package com.incubator.edupayroll.dto.record;

import com.incubator.edupayroll.dto.teacher.Teacher;
import com.incubator.edupayroll.entity.record.RecordType;
import lombok.Value;

@Value
public class Record {

  int line;
  RecordType type;
  String information;
  Teacher teacher;
}
