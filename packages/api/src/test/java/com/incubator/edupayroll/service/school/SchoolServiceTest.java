package com.incubator.edupayroll.service.school;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.school.SchoolEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class SchoolServiceTest {
  @Autowired private Faker faker;

  @Autowired private TestHelper helper;

  @Autowired private SchoolService schoolService;

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create school correctly")
  public void testSchoolServiceCreate() {
    var user = helper.createUser();

    var schoolName = faker.company().name();
    var editorName = faker.name().fullName();
    var editorTitle = faker.job().title();
    var principalName = faker.name().fullName();

    var teacher = schoolService.create(user, schoolName, editorName, editorTitle, principalName);

    assertNotNull(teacher);

    assertEquals(teacher.getName(), schoolName);
    assertEquals(teacher.getEditorName(), editorName);
    assertEquals(teacher.getEditorTitle(), editorTitle);
    assertEquals(teacher.getPrincipalName(), principalName);
    assertEquals(teacher.getUser().getId(), user.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update school correctly")
  public void testSchoolServiceUpdate() {
    var user = helper.createUser();
    var school = createSchool(user);

    var schoolName = school.getName();
    var editorName = school.getEditorName();
    var editorTitle = school.getEditorTitle();
    var principalName = school.getPrincipalName();

    var updatedSchoolName = faker.company().name();

    var updatedSchool = schoolService.update(school, updatedSchoolName, null, null, null);

    assertNotNull(updatedSchool);
    assertNotEquals(updatedSchool.getName(), schoolName);

    assertEquals(updatedSchool.getName(), updatedSchoolName);
    assertEquals(updatedSchool.getEditorName(), editorName);
    assertEquals(updatedSchool.getEditorTitle(), editorTitle);
    assertEquals(updatedSchool.getPrincipalName(), principalName);
  }

  private SchoolEntity createSchool(UserEntity user) {
    var schoolName = faker.company().name();
    var editorName = faker.name().fullName();
    var editorTitle = faker.job().title();
    var principalName = faker.name().fullName();

    return schoolService.create(user, schoolName, editorName, editorTitle, principalName);
  }
}
