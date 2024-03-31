package com.incubator.edupayroll.service.school;

import com.incubator.edupayroll.dto.school.SchoolCreateDTO;
import com.incubator.edupayroll.dto.school.SchoolDTO;
import com.incubator.edupayroll.dto.school.SchoolUpdateDTO;
import com.incubator.edupayroll.entity.SchoolEntity;
import com.incubator.edupayroll.entity.UserEntity;
import com.incubator.edupayroll.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public SchoolDTO create(UserEntity user, SchoolCreateDTO schoolCreateDTO) {
        var school = mapToSchoolEntity(user, schoolCreateDTO);
        return mapToSchoolDTO(schoolRepository.saveAndFlush(school));
    }

    public SchoolDTO getById(UUID id) {
        var maybeSchool = schoolRepository.findById(id);
        var school = maybeSchool.orElseThrow(() -> new SchoolNotFoundException(id));
        return mapToSchoolDTO(school);
    }

    public SchoolDTO update(UUID id, SchoolUpdateDTO schoolUpdateDTO) {
        var maybeSchool = schoolRepository.findById(id);
        var school = maybeSchool.orElseThrow(() -> new SchoolNotFoundException(id));

        if (schoolUpdateDTO.getName() != null)
            school.setName(schoolUpdateDTO.getName());

        if (schoolUpdateDTO.getEditorName() != null)
            school.setName(schoolUpdateDTO.getEditorName());

        if (schoolUpdateDTO.getEditorTitle() != null)
            school.setName(schoolUpdateDTO.getEditorTitle());

        if (schoolUpdateDTO.getPrincipalName() != null)
            school.setName(schoolUpdateDTO.getPrincipalName());

        schoolRepository.save(school);

        return mapToSchoolDTO(school);
    }

    private SchoolDTO mapToSchoolDTO(SchoolEntity school) {
        return new SchoolDTO(
                school.getId(),
                school.getName(),
                school.getEditorName(),
                school.getEditorTitle(),
                school.getPrincipalName()
        );
    }

    private SchoolEntity mapToSchoolEntity(UserEntity userEntity, SchoolDTO schoolDTO) {
        return new SchoolEntity(
                schoolDTO.getName(),
                schoolDTO.getEditorName(),
                schoolDTO.getEditorTitle(),
                schoolDTO.getPrincipalName(),
                userEntity
        );
    }

    private SchoolEntity mapToSchoolEntity(UserEntity userEntity, SchoolCreateDTO schoolCreateDTO) {
        return new SchoolEntity(
                schoolCreateDTO.getName(),
                schoolCreateDTO.getEditorName(),
                schoolCreateDTO.getEditorTitle(),
                schoolCreateDTO.getPrincipalName(),
                userEntity
        );
    }

}
