package com.incubator.edupayroll.mapper.record;

import com.incubator.edupayroll.dto.record.Record;
import com.incubator.edupayroll.entity.base.BaseEntity;
import com.incubator.edupayroll.entity.record.RecordEntity;
import com.incubator.edupayroll.mapper.teacher.TeacherMapper;
import java.util.Optional;

public class RecordMapper {
  public static Record toDTO(RecordEntity record) {
    var teacher = TeacherMapper.toDTO(record.getTeacher());

    return new Record(
        record.getId(),
        Optional.ofNullable(record.getNext()).map(BaseEntity::getId).orElse(null),
        record.getType(),
        teacher,
        record.getHours(),
        record.getCreatedAt(),
        record.getUpdatedAt());
  }
}
