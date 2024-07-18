package com.incubator.edupayroll.mapper.teacher;

import com.incubator.edupayroll.dto.teacher.Teacher;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;

public class TeacherMapper {
  public static Teacher toDTO(TeacherEntity teacher) {
    return new Teacher(
        teacher.getId(),
        teacher.getFirstName(),
        teacher.getLastName(),
        teacher.getBranch(),
        teacher.getIdNumber());
  }
}
