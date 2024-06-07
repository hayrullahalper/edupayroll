package com.incubator.edupayroll.mapper.school;

import com.incubator.edupayroll.dto.school.SchoolDTO;
import com.incubator.edupayroll.entity.School;

public class SchoolMapper {
    public static SchoolDTO toDTO(School school) {
        return new SchoolDTO(
                school.getId(),
                school.getName(),
                school.getEditorName(),
                school.getEditorTitle(),
                school.getPrincipalName()
        );
    }
}
