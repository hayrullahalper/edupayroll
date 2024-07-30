package com.incubator.edupayroll.service.teacher;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import com.incubator.edupayroll.util.selection.SelectionType;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class TeacherServiceTest {
  @Autowired private Faker faker;

  @Autowired private TestHelper helper;

  @Autowired private TeacherService teacherService;

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create teacher correctly")
  public void testTeacherServiceCreate() {
    var user = helper.createUser();

    var name = faker.name().fullName();
    var branch = faker.job().field();
    var idNumber = faker.number().digits(11);
    var description = faker.name().title();

    var teacher = teacherService.create(name, branch, idNumber, description, user);

    assertNotNull(teacher);

    assertEquals(teacher.getName(), name);
    assertEquals(teacher.getBranch(), branch);
    assertEquals(teacher.getIdNumber(), idNumber);
    assertEquals(teacher.getDescription(), description);
    assertEquals(teacher.getUser().getId(), user.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update teacher correctly")
  public void testTeacherServiceUpdate() {
    var user = helper.createUser();
    var teacher = createTeacher(user);

    var name = teacher.getName();
    var branch = teacher.getBranch();
    var updatedName = faker.name().fullName();

    var updatedTeacher = teacherService.update(teacher, updatedName, null, null, null);

    assertNotNull(updatedTeacher);

    assertNotEquals(updatedTeacher.getName(), name);
    assertEquals(updatedTeacher.getName(), updatedName);

    assertEquals(updatedTeacher.getBranch(), branch);
    assertEquals(updatedTeacher.getId(), teacher.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove teacher correctly")
  public void testTeacherServiceRemove() {
    var user = helper.createUser();
    var teacher = createTeacher(user);

    assertEquals(1, teacherService.count(user, Optional.empty()));

    teacherService.remove(teacher);

    assertEquals(0, teacherService.count(user, Optional.empty()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should bulk remove teachers correctly")
  public void testTeacherServiceBulkRemove() {
    var user = helper.createUser();

    var teacher1 = createTeacher(user);
    var teacher2 = createTeacher(user);
    var teacher3 = createTeacher(user);
    var teacher4 = createTeacher(user);

    assertEquals(4, teacherService.count(user, Optional.empty()));

    teacherService.bulkRemove(
        user, SelectionType.INCLUDE, List.of(teacher1.getId(), teacher2.getId()));

    assertEquals(2, teacherService.count(user, Optional.empty()));
    assertEquals(0, teacherService.count(user, Optional.of(teacher1.getName())));
    assertEquals(0, teacherService.count(user, Optional.of(teacher2.getName())));

    teacherService.bulkRemove(user, SelectionType.EXCLUDE, List.of(teacher3.getId()));

    assertEquals(1, teacherService.count(user, Optional.empty()));
    assertEquals(1, teacherService.count(user, Optional.of(teacher3.getName())));
    assertEquals(0, teacherService.count(user, Optional.of(teacher4.getName())));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get teacher by id correctly")
  public void testTeacherServiceGetById() {
    var user = helper.createUser();
    var teacher = createTeacher(user);

    var foundTeacher = teacherService.getById(user, teacher.getId());

    assertNotNull(foundTeacher);

    assertEquals(foundTeacher.getId(), teacher.getId());
    assertEquals(foundTeacher.getName(), teacher.getName());
    assertEquals(foundTeacher.getUser().getId(), user.getId());
    assertEquals(foundTeacher.getBranch(), teacher.getBranch());
    assertEquals(foundTeacher.getIdNumber(), teacher.getIdNumber());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all teachers correctly")
  public void testTeacherServiceGetAll() {
    var user = helper.createUser();
    var anotherUser = helper.createUser();

    var teachers = new ArrayList<TeacherEntity>();

    for (int i = 0; i < 12; i++) teachers.add(createTeacher(user));

    var reversed = teachers.reversed();

    for (int i = 0; i < 5; i++) createTeacher(anotherUser);

    var teachersPageOne = teacherService.getAll(user, 10, 0, Optional.empty());
    var teachersPageTwo = teacherService.getAll(user, 10, 10, Optional.empty());

    assertEquals(10, teachersPageOne.size());
    assertEquals(2, teachersPageTwo.size());

    for (int i = 0; i < teachersPageOne.size(); i++)
      assertEquals(teachersPageOne.get(i).getId(), reversed.get(i).getId());

    for (var i = 10; i < teachersPageTwo.size() + 10; i++)
      assertEquals(teachersPageTwo.get(i - 10).getId(), reversed.get(i).getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all teachers correctly with search query")
  public void testTeacherServiceGetAllWithSearchQuery() {
    var user = helper.createUser();
    var teacher1 = createTeacher(user);
    createTeacher(user);

    var teachers = teacherService.getAll(user, 10, 0, Optional.of(teacher1.getName()));

    assertEquals(1, teachers.size());
    assertEquals(teachers.getFirst().getId(), teacher1.getId());

    teachers = teacherService.getAll(user, 10, 0, Optional.of(teacher1.getBranch()));

    assertEquals(1, teachers.size());
    assertEquals(teachers.getFirst().getId(), teacher1.getId());

    teachers = teacherService.getAll(user, 10, 0, Optional.of(teacher1.getIdNumber()));

    assertEquals(1, teachers.size());
    assertEquals(teachers.getFirst().getId(), teacher1.getId());

    teachers = teacherService.getAll(user, 10, 0, Optional.of(teacher1.getName().substring(0, 3)));

    assertEquals(1, teachers.size());
    assertEquals(teachers.getFirst().getId(), teacher1.getId());

    teachers = teacherService.getAll(user, 10, 0, Optional.of(faker.name().fullName()));

    assertEquals(0, teachers.size());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should count teachers correctly")
  public void testTeacherServiceCount() {
    var user = helper.createUser();

    assertEquals(0, teacherService.count(user, Optional.empty()));

    for (int i = 0; i < 5; i++) createTeacher(user);

    assertEquals(5, teacherService.count(user, Optional.empty()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should count teachers correctly with search query")
  public void testTeacherServiceCountWithSearchQuery() {
    var user = helper.createUser();

    var teacher1 = createTeacher(user);
    createTeacher(user);

    assertEquals(2, teacherService.count(user, Optional.empty()));

    assertEquals(1, teacherService.count(user, Optional.of(teacher1.getName())));
    assertEquals(1, teacherService.count(user, Optional.of(teacher1.getBranch())));
    assertEquals(1, teacherService.count(user, Optional.of(teacher1.getIdNumber())));
    assertEquals(1, teacherService.count(user, Optional.of(teacher1.getName().substring(0, 3))));

    assertEquals(0, teacherService.count(user, Optional.of(faker.name().fullName())));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw exception when teacher not found")
  public void testTeacherServiceGetByIdNotFound() {
    var user = helper.createUser();
    var teacherId = UUID.fromString(faker.internet().uuid());

    assertThrows(TeacherNotFoundException.class, () -> teacherService.getById(user, teacherId));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw exception when user is not owner of teacher, when getting by id")
  public void testTeacherServiceGetByIdAccessDenied() {
    var user = helper.createUser();
    var anotherUser = helper.createUser();
    var teacher = createTeacher(anotherUser);

    assertThrows(AccessDeniedException.class, () -> teacherService.getById(user, teacher.getId()));
  }

  private TeacherEntity createTeacher(UserEntity user) {
    var name = faker.name().fullName();
    var branch = faker.job().field();
    var idNumber = faker.number().digits(11);
    var description = faker.name().title();

    return teacherService.create(name, branch, idNumber, description, user);
  }
}
