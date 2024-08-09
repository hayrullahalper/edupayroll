package com.incubator.edupayroll.controller.teacher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.TeacherRepository;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.selection.SelectionType;
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
      helper.createTeacher(mockedUser);
    }

    mvc.perform(get("/teachers?limit=10&offset=0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("nodes").isArray())
        .andExpect(jsonPath("meta.limit").value(10))
        .andExpect(jsonPath("meta.offset").value(0))
        .andExpect(jsonPath("meta.total").value(12))
        .andExpect(jsonPath("nodes.length()").value(10));

    mvc.perform(get("/teachers?limit=10&offset=10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("nodes").isArray())
        .andExpect(jsonPath("meta.limit").value(10))
        .andExpect(jsonPath("meta.offset").value(10))
        .andExpect(jsonPath("meta.total").value(12))
        .andExpect(jsonPath("nodes.length()").value(2));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all teachers with search query")
  public void testGetTeachersWithNameFilter() throws Exception {
    var teacher1 = helper.createTeacher(mockedUser);
    var teacher2 = helper.createTeacher(mockedUser);

    mvc.perform(get("/teachers?limit=10&offset=0&query=" + teacher1.getName()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("nodes.length()").value(1))
        .andExpect(jsonPath("nodes[0].name").value(teacher1.getName()));

    mvc.perform(get("/teachers?limit=10&offset=0&query=" + teacher2.getName()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("nodes.length()").value(1))
        .andExpect(jsonPath("nodes[0].name").value(teacher2.getName()));

    mvc.perform(get("/teachers?limit=10&offset=0&query=notfound"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("nodes").isArray())
        .andExpect(jsonPath("nodes.length()").value(0));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create a teacher")
  public void testCreateTeacher() throws Exception {
    var name = faker.name().fullName();
    var branch = faker.name().fullName();
    var idNumber = faker.idNumber().valid();
    var description = faker.name().title();

    mvc.perform(
            post("/teachers")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "name",
                            name,
                            "branch",
                            branch,
                            "idNumber",
                            idNumber,
                            "description",
                            description))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.name").value(name))
        .andExpect(jsonPath("node.branch").value(branch))
        .andExpect(jsonPath("node.idNumber").value(idNumber))
        .andExpect(jsonPath("node.description").value(description));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update a teacher")
  public void testUpdateTeacher() throws Exception {
    var teacher = helper.createTeacher(mockedUser);

    var branch = teacher.getBranch();
    var idNumber = teacher.getIdNumber();

    var updatedName = faker.name().fullName();

    mvc.perform(
            put("/teachers/" + teacher.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", updatedName))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.branch").value(branch))
        .andExpect(jsonPath("node.name").value(updatedName))
        .andExpect(jsonPath("node.idNumber").value(idNumber));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should delete a teacher")
  public void testDeleteTeacher() throws Exception {
    var teacher = helper.createTeacher(mockedUser);
    var teacherId = teacher.getId();

    mvc.perform(delete("/teachers/" + teacher.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.success").value(true));

    var maybeTeacher = teacherRepository.findById(teacherId);
    assertTrue(maybeTeacher.isEmpty());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should bulk delete teachers")
  public void testBulkDeleteTeachers() throws Exception {
    var teacher1 = helper.createTeacher(mockedUser);
    var teacher2 = helper.createTeacher(mockedUser);
    var teacher3 = helper.createTeacher(mockedUser);
    helper.createTeacher(mockedUser);

    mvc.perform(
            delete("/teachers/bulk")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "ids",
                            new String[] {
                              teacher1.getId().toString(), teacher2.getId().toString()
                            }))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.success").value(true));

    mvc.perform(
            delete("/teachers/bulk")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "ids",
                            new String[] {teacher3.getId().toString()},
                            "type",
                            SelectionType.EXCLUDE))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.success").value(true));
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
    var teacher = helper.createTeacher(mockedUser);
    var anotherUser = helper.createUser();

    when(userService.getAuthenticatedUser()).thenReturn(anotherUser);

    mvc.perform(
            put("/teachers/" + teacher.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", "updated name"))))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("errors[0].code").value("ACCESS_DENIED"));
  }
}
