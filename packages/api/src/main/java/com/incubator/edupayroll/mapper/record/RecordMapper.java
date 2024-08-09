package com.incubator.edupayroll.mapper.record;

import com.incubator.edupayroll.dto.record.Record;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.mapper.teacher.TeacherMapper;

public class RecordMapper {
  public static Record toDTO(RecordEntity record) {
    return new Record(
        record.getNextId(),
        record.getType(),
        record.getInformation(),
        TeacherMapper.toDTO(record.getTeacher()));
  }
}
