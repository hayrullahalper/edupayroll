package com.incubator.edupayroll.service.school;

import com.incubator.edupayroll.entity.School;
import com.incubator.edupayroll.entity.User;
import com.incubator.edupayroll.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School create(User user, String schoolName, String editorName, String editorTitle, String principalName) {
        var school = new School();

        school.setUser(user);

        school.setName(schoolName);
        school.setEditorName(editorName);
        school.setEditorTitle(editorTitle);
        school.setPrincipalName(principalName);

        return schoolRepository.saveAndFlush(school);
    }

    public School update(School school, String schoolName, String editorName, String editorTitle, String principalName) {
        if (schoolName != null)
            school.setName(schoolName);

        if (editorName != null)
            school.setEditorName(editorName);

        if (editorTitle != null)
            school.setEditorTitle(editorTitle);

        if (principalName != null)
            school.setPrincipalName(principalName);

        return schoolRepository.saveAndFlush(school);
    }

    public School getByUser(User user) {
        var schools = schoolRepository.findByUser(user);

        if (schools.isEmpty()) {
            throw SchoolNotFoundException.byUser(user);
        }

        return schools.getFirst();
    }
}
