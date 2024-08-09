package com.incubator.edupayroll.helper;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.school.SchoolEntity;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.repository.DocumentRepository;
import com.incubator.edupayroll.repository.SchoolRepository;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.repository.UserRepository;
import java.time.YearMonth;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestHelper {
  @Autowired private Faker faker;
  @Autowired private UserRepository userRepository;
  @Autowired private SchoolRepository schoolRepository;
  @Autowired private TeacherRepository teacherRepository;
  @Autowired private DocumentRepository documentRepository;

  public UserEntity createUser() {
    var user = new UserEntity();

    user.setFirstName(faker.name().firstName());
    user.setLastName(faker.name().lastName());
    user.setEmail(faker.internet().emailAddress());
    user.setPasswordHash(faker.internet().password());

    return userRepository.saveAndFlush(user);
  }

  public SchoolEntity createSchool(UserEntity user) {
    var schoolName = faker.name().name();
    var editorName = faker.name().fullName();
    var editorTitle = faker.name().title();
    var principalName = faker.name().fullName();

    var school = new SchoolEntity();

    school.setUser(user);

    school.setName(schoolName);
    school.setEditorName(editorName);
    school.setEditorTitle(editorTitle);
    school.setPrincipalName(principalName);

    return schoolRepository.saveAndFlush(school);
  }

  public TeacherEntity createTeacher(UserEntity user) {
    var name = faker.name().fullName();
    var branch = faker.job().field();
    var idNumber = faker.number().digits(11);
    var description = faker.name().title();

    var teacher = new TeacherEntity(name, branch, idNumber, description, user, new ArrayList<>());
    return teacherRepository.saveAndFlush(teacher);
  }

  public DocumentEntity createDocument(UserEntity user) {
    var document = new DocumentEntity();

    var futureDate = faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault());
    var time = YearMonth.of(futureDate.getYear(), futureDate.getMonth());

    document.setName(faker.name().name());
    document.setTime(time);
    document.setUser(user);
    document.setRecords(new ArrayList<>());
    document.setExports(new ArrayList<>());

    return documentRepository.saveAndFlush(document);
  }
}
