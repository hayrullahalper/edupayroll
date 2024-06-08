package com.incubator.edupayroll.mapper.school;

import com.incubator.edupayroll.dto.school.School;
import com.incubator.edupayroll.entity.school.SchoolEntity;

public class SchoolMapper {
    public static School toDTO(SchoolEntity school) {
        return new School(
                school.getId(),
                school.getName(),
                school.getEditorName(),
                school.getEditorTitle(),
                school.getPrincipalName()
        );
    }
}
