package com.incubator.edupayroll.controller.teacher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.service.user.UserService;
import jakarta.transaction.Transactional;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TeacherControllerTest {
  @Autowired private MockMvc mvc;

  @Autowired private Faker faker;

  @Autowired private TestHelper helper;

  @Autowired private ObjectMapper mapper;

  @MockBean private UserService userService;

  @Autowired private TeacherRepository teacherRepository;

  private UserEntity mockedUser;

  @BeforeEach
  public void setup() {
    mockedUser = helper.createUser();
    when(userService.getAuthenticatedUser()).thenReturn(mockedUser);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all teachers")
  public void testGetTeachers() throws Exception {
    for (int i = 0; i < 12; i++) {
      createTeacher();
    }

    mvc.perform(get("/teachers?limit=10&offset=0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data").isArray())
        .andExpect(jsonPath("meta.page").value(1))
        .andExpect(jsonPath("meta.size").value(10))
        .andExpect(jsonPath("meta.total").value(2))
        .andExpect(jsonPath("data.length()").value(10));

    mvc.perform(get("/teachers?limit=10&offset=10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data").isArray())
        .andExpect(jsonPath("meta.page").value(2))
        .andExpect(jsonPath("meta.size").value(10))
        .andExpect(jsonPath("meta.total").value(2))
        .andExpect(jsonPath("data.length()").value(2));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create a teacher")
  public void testCreateTeacher() throws Exception {
    var name = faker.name().fullName();
    var branch = faker.name().fullName();
    var idNumber = faker.idNumber().valid();

    mvc.perform(
            post("/teachers")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "name", name,
                            "branch", branch,
                            "idNumber", idNumber))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data.name").value(name))
        .andExpect(jsonPath("data.branch").value(branch))
        .andExpect(jsonPath("data.identityNo").value(idNumber));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update a teacher")
  public void testUpdateTeacher() throws Exception {
    var teacher = createTeacher();

    var branch = teacher.getBranch();
    var idNumber = teacher.getIdNumber();

    var updatedName = faker.name().fullName();

    mvc.perform(
            put("/teachers/" + teacher.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", updatedName))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data.branch").value(branch))
        .andExpect(jsonPath("data.name").value(updatedName))
        .andExpect(jsonPath("data.identityNo").value(idNumber));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should delete a teacher")
  public void testDeleteTeacher() throws Exception {
    var teacher = createTeacher();
    var teacherId = teacher.getId();

    mvc.perform(delete("/teachers/" + teacher.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("data.success").value(true));

    var maybeTeacher = teacherRepository.findById(teacherId);
    assertTrue(maybeTeacher.isEmpty());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should return not found error when teacher not found")
  public void testTeacherNotFound() throws Exception {
    mvc.perform(
            put("/teachers/" + faker.internet().uuid())
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", "updated name"))))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("errors[0].code").value("TEACHER_NOT_FOUND"));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should return access denied error when user is not owner of teacher")
  public void testTeacherAccessDenied() throws Exception {
    var teacher = createTeacher();
    var anotherUser = helper.createUser();

    when(userService.getAuthenticatedUser()).thenReturn(anotherUser);

    mvc.perform(
            put("/teachers/" + teacher.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", "updated name"))))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("errors[0].code").value("ACCESS_DENIED"));
  }

  private TeacherEntity createTeacher() {
    var firstName = faker.name().firstName();
    var lastName = faker.name().lastName();
    var branch = faker.name().fullName();
    var idNumber = faker.idNumber().valid();

    var teacher = new TeacherEntity(firstName, lastName, branch, idNumber, mockedUser, null);

    return teacherRepository.saveAndFlush(teacher);
  }
}
