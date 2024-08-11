package com.incubator.edupayroll.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordType;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.RecordRepository;
import com.incubator.edupayroll.service.user.UserService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
public class RecordControllerTest {
  @Autowired private Faker faker;
  @Autowired private MockMvc mvc;
  @Autowired private TestHelper helper;
  @Autowired private ObjectMapper mapper;
  @MockBean private UserService userService;
  @Autowired private RecordRepository recordRepository;

  private UserEntity mockedUser;
  private TeacherEntity mockedTeacher;
  private DocumentEntity mockedDocument;
  private final RecordType type = RecordType.Course101;
  private final List<Integer> hours = List.of(0, 0, 0, 0);

  @BeforeEach
  public void setup() {
    mockedUser = helper.createUser();
    mockedTeacher = helper.createTeacher(mockedUser);
    mockedDocument = helper.createDocument(mockedUser);

    when(userService.getAuthenticatedUser()).thenReturn(mockedUser);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create a record in an empty document")
  public void testCreateRecord() throws Exception {
    mvc.perform(
            post("/records")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "documentId",
                            mockedDocument.getId(),
                            "teacherId",
                            mockedTeacher.getId(),
                            "type",
                            type,
                            "hours",
                            hours))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.type").value(type.toString()))
        .andExpect(jsonPath("node.hours").isArray())
        .andExpect(jsonPath("node.hours.length()").value(4));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create a record with previous record")
  public void testCreateRecordWithPrevious() throws Exception {
    var previous = helper.createRecord(mockedDocument, mockedTeacher);

    mvc.perform(
            post("/records")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "documentId",
                            mockedDocument.getId(),
                            "teacherId",
                            mockedTeacher.getId(),
                            "type",
                            type,
                            "hours",
                            hours,
                            "previousId",
                            previous.getId()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.type").value(type.toString()))
        .andExpect(jsonPath("node.hours").isArray())
        .andExpect(jsonPath("node.hours.length()").value(4));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update basic record information")
  public void testUpdateRecord() throws Exception {
    var record = helper.createRecord(mockedDocument, mockedTeacher);

    var type = faker.options().option(RecordType.values());
    var teacher = helper.createTeacher(mockedUser);

    mvc.perform(
            put("/records/" + record.getId())
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(Map.of("teacherId", teacher.getId(), "type", type))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.type").value(type.toString()))
        .andExpect(jsonPath("node.teacher.id").value(teacher.getId().toString()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update record hours")
  public void testUpdateRecordHours() throws Exception {
    var record = helper.createRecord(mockedDocument, mockedTeacher);

    var hours = List.of(1, 2, 3, 4);

    mvc.perform(
            put("/records/" + record.getId() + "/hours")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("hours", hours))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.hours").isArray())
        .andExpect(jsonPath("node.hours.length()").value(4))
        .andExpect(jsonPath("node.hours[0]").value(1))
        .andExpect(jsonPath("node.hours[1]").value(2))
        .andExpect(jsonPath("node.hours[2]").value(3))
        .andExpect(jsonPath("node.hours[3]").value(4));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update record order")
  public void testUpdateRecordOrder() throws Exception {
    var record3 = helper.createRecord(mockedDocument, mockedTeacher);
    var record2 = helper.createRecord(mockedDocument, mockedTeacher);
    var record1 = helper.createRecord(mockedDocument, mockedTeacher);

    mvc.perform(
            put("/records/" + record3.getId() + "/previous")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("previousId", record1.getId()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty())
        .andExpect(jsonPath("node.id").value(record3.getId().toString()))
        .andExpect(jsonPath("node.nextId").value(record2.getId().toString()));

    var maybeRecord3 = recordRepository.findById(record3.getId());
    var maybeRecord2 = recordRepository.findById(record2.getId());
    var maybeRecord1 = recordRepository.findById(record1.getId());

    assertTrue(maybeRecord3.isPresent());
    assertTrue(maybeRecord2.isPresent());
    assertTrue(maybeRecord1.isPresent());

    assertTrue(maybeRecord1.get().isHead());
    assertNull(maybeRecord2.get().getNext());
    assertEquals(maybeRecord3.get().getNext().getId(), record2.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove record")
  public void testRemoveRecord() throws Exception {
    var record3 = helper.createRecord(mockedDocument, mockedTeacher);
    var record2 = helper.createRecord(mockedDocument, mockedTeacher);
    var record1 = helper.createRecord(mockedDocument, mockedTeacher);

    mvc.perform(delete("/records/" + record2.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("errors").isEmpty());

    var maybeRecord3 = recordRepository.findById(record3.getId());
    var maybeRecord2 = recordRepository.findById(record2.getId());
    var maybeRecord1 = recordRepository.findById(record1.getId());

    assertTrue(maybeRecord3.isPresent());
    assertTrue(maybeRecord1.isPresent());
    assertFalse(maybeRecord2.isPresent());

    assertTrue(maybeRecord1.get().isHead());
    assertNull(maybeRecord3.get().getNext());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get record not found error")
  public void testRecordNotFound() throws Exception {
    mvc.perform(
            put("/records/" + faker.internet().uuid())
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of("teacherId", mockedTeacher.getId(), "type", type))))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("errors").isNotEmpty())
        .andExpect(jsonPath("errors[0].code").value("RECORD_NOT_FOUND"));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get teacher not found error")
  public void testTeacherNotFound() throws Exception {
    var record = helper.createRecord(mockedDocument, mockedTeacher);

    mvc.perform(
            put("/records/" + record.getId())
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of("teacherId", faker.internet().uuid(), "type", type))))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("errors").isNotEmpty())
        .andExpect(jsonPath("errors[0].code").value("TEACHER_NOT_FOUND"));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get document not found error")
  public void testDocumentNotFound() throws Exception {
    mvc.perform(
            post("/records")
                .contentType("application/json")
                .content(
                    mapper.writeValueAsString(
                        Map.of(
                            "documentId",
                            faker.internet().uuid(),
                            "teacherId",
                            mockedTeacher.getId(),
                            "type",
                            type,
                            "hours",
                            hours))))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("errors").isNotEmpty())
        .andExpect(jsonPath("errors[0].code").value("DOCUMENT_NOT_FOUND"));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get record hours too long error")
  public void testRecordHoursTooLong() throws Exception {
    var record = helper.createRecord(mockedDocument, mockedTeacher);

    var hours = new ArrayList<>();

    for (int i = 0; i < 32; i++) {
      hours.add(0);
    }

    mvc.perform(
            put("/records/" + record.getId() + "/hours")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("hours", hours))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("errors").isNotEmpty())
        .andExpect(jsonPath("errors[0].code").value("RECORD_HOURS_TOO_LONG"));
  }
}
