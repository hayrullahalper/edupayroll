package com.incubator.edupayroll.mapper.teacher;

import com.incubator.edupayroll.dto.teacher.TeacherDTO;
import com.incubator.edupayroll.entity.Teacher;

public class TeacherMapper {
    public static TeacherDTO toDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getBranch(),
                teacher.getIdNumber()
        );
    }
}
