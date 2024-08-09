package com.incubator.edupayroll.service.school;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.javafaker.Faker;
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

    var schoolName = faker.name().name();
    var editorName = faker.name().fullName();
    var editorTitle = faker.name().title();
    var principalName = faker.name().fullName();

    var school = schoolService.create(user, schoolName, editorName, editorTitle, principalName);

    assertNotNull(school);

    assertEquals(school.getUser().getId(), user.getId());
    assertEquals(school.getName(), schoolName);
    assertEquals(school.getEditorName(), editorName);
    assertEquals(school.getEditorTitle(), editorTitle);
    assertEquals(school.getPrincipalName(), principalName);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update school correctly")
  public void testSchoolServiceUpdate() {
    var user = helper.createUser();
    var school = helper.createSchool(user);

    var schoolName = faker.name().name();
    var editorName = faker.name().fullName();
    var editorTitle = faker.name().title();
    var principalName = faker.name().fullName();
    var updatedSchool =
        schoolService.update(school, schoolName, editorName, editorTitle, principalName);

    assertNotNull(updatedSchool);

    assertEquals(updatedSchool.getName(), schoolName);
    assertEquals(updatedSchool.getEditorName(), editorName);
    assertEquals(updatedSchool.getEditorTitle(), editorTitle);
    assertEquals(updatedSchool.getPrincipalName(), principalName);
  }

  @Test
  @DisplayName("should get school correctly")
  public void testSchoolServiceGet() {
    var user = helper.createUser();
    var school = helper.createSchool(user);

    var schoolByUser = schoolService.getByUser(user);

    assertNotNull(schoolByUser);

    assertEquals(schoolByUser.getId(), school.getId());
    assertEquals(schoolByUser.getUser().getId(), user.getId());
  }
}
