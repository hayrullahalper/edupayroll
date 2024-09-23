package com.incubator.edupayroll.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.DocumentRepository;
import com.incubator.edupayroll.repository.ExportRepository;
import com.incubator.edupayroll.repository.RecordRepository;
import com.incubator.edupayroll.service.export.ExportProducer;
import com.incubator.edupayroll.service.user.UserService;
import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
public class DocumentControllerTest {
  @Autowired private Faker faker;
  @Autowired private MockMvc mvc;
  @Autowired private TestHelper helper;
  @Autowired private ObjectMapper mapper;
  @MockBean private UserService userService;
  @MockBean private ExportProducer exportProducer;
  @Autowired private RecordRepository recordRepository;
  @Autowired private ExportRepository exportRepository;
  @Autowired private DocumentRepository documentRepository;

  private UserEntity mockedUser;
  private TeacherEntity mockedTeacher;

  @BeforeEach
  public void setup() {
    mockedUser = helper.createUser();
    mockedTeacher = helper.createTeacher(mockedUser);
    when(userService.getAuthenticatedUser()).thenReturn(mockedUser);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all documents without records and exports")
  public void testGetDocumentsWithoutOtherEntities() throws Exception {
    var document = helper.createDocument(mockedUser);

    helper.createDocument(mockedUser);
    helper.createDocument(mockedUser);

    helper.createExport(document);
    helper.createRecord(document, mockedTeacher);

    mvc.perform(get("/documents?limit=2&offset=0"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.length()").value(2))
        .andExpect(jsonPath("$.meta.offset").value(0))
        .andExpect(jsonPath("$.meta.limit").value(2))
        .andExpect(jsonPath("$.meta.total").value(3));

    mvc.perform(get("/documents?limit=2&offset=2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.length()").value(1))
        .andExpect(jsonPath("$.meta.offset").value(2))
        .andExpect(jsonPath("$.meta.limit").value(2))
        .andExpect(jsonPath("$.meta.total").value(3))
        .andExpect(jsonPath("$.data[0].id").value(document.getId().toString()))
        .andExpect(jsonPath("$.data[0].name").value(document.getName()))
        .andExpect(jsonPath("$.data[0].exports").isArray())
        .andExpect(jsonPath("$.data[0].exports").isEmpty())
        .andExpect(jsonPath("$.data[0].records").isArray())
        .andExpect(jsonPath("$.data[0].records").isEmpty());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all documents with name filter")
  public void testGetDocumentsWithNameFilter() throws Exception {
    var document1 = helper.createDocument(mockedUser);
    var document2 = helper.createDocument(mockedUser);

    mvc.perform(get("/documents?limit=10&offset=0&name=" + document1.getName()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()").value(1))
        .andExpect(jsonPath("$.data[0].name").value(document1.getName()));

    mvc.perform(get("/documents?limit=10&offset=0&name=" + document2.getName()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()").value(1))
        .andExpect(jsonPath("$.data[0].name").value(document2.getName()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get a document by id")
  public void testGetDocument() throws Exception {
    var document = helper.createDocument(mockedUser);

    var export = helper.createExport(document);
    var record = helper.createRecord(document, mockedTeacher);

    mvc.perform(get("/documents/" + document.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.id").value(document.getId().toString()))
        .andExpect(jsonPath("$.data.name").value(document.getName()))
        .andExpect(jsonPath("$.data.exports").isArray())
        .andExpect(jsonPath("$.data.exports.length()").value(1))
        .andExpect(jsonPath("$.data.exports[0].id").value(export.getId().toString()))
        .andExpect(jsonPath("$.data.exports[0].name").value(export.getName()))
        .andExpect(jsonPath("$.data.exports[0].status").value(export.getStatus().name()))
        .andExpect(jsonPath("$.data.records").isArray())
        .andExpect(jsonPath("$.data.records.length()").value(1))
        .andExpect(jsonPath("$.data.records[0].id").value(record.getId().toString()))
        .andExpect(jsonPath("$.data.records[0].type").value(record.getType().name()))
        .andExpect(
            jsonPath("$.data.records[0].teacher.id").value(record.getTeacher().getId().toString()))
        .andExpect(jsonPath("$.data.records[0].teacher.name").value(record.getTeacher().getName()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update a document")
  public void testUpdateDocument() throws Exception {
    var document = helper.createDocument(mockedUser);

    var name = faker.name().title();
    var date = faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault());
    var time = YearMonth.of(date.getYear(), date.getMonth());

    mvc.perform(
            put("/documents/" + document.getId())
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", name, "time", time.toString()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.id").value(document.getId().toString()))
        .andExpect(jsonPath("$.data.name").value(name))
        .andExpect(jsonPath("$.data.time").value(time.toString()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create a document")
  public void testCreateDocument() throws Exception {
    var name = faker.name().title();
    var date = faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault());
    var time = YearMonth.of(date.getYear(), date.getMonth());

    mvc.perform(
            post("/documents")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", name, "time", time.toString()))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.name").value(name))
        .andExpect(jsonPath("$.data.time").value(time.toString()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should delete a document")
  public void testDeleteDocument() throws Exception {
    var document = helper.createDocument(mockedUser);

    mvc.perform(delete("/documents/" + document.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.success").value(true));

    assertFalse(documentRepository.existsById(document.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should clear all records of a document")
  public void testClearDocumentRecords() throws Exception {
    var document = helper.createDocument(mockedUser);

    var record1 = helper.createRecord(document, mockedTeacher);
    var record2 = helper.createRecord(document, mockedTeacher);

    mvc.perform(put("/documents/" + document.getId() + "/clear/records"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.success").value(true));

    assertFalse(recordRepository.existsById(record1.getId()));
    assertFalse(recordRepository.existsById(record2.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should clear all exports of a document")
  public void testClearDocumentExports() throws Exception {
    var document = helper.createDocument(mockedUser);

    var export1 = helper.createExport(document);
    var export2 = helper.createExport(document);

    mvc.perform(put("/documents/" + document.getId() + "/clear/exports"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.success").value(true));

    assertFalse(exportRepository.existsById(export1.getId()));
    assertFalse(exportRepository.existsById(export2.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should generate a export of a document")
  public void testGenerateDocumentExport() throws Exception {
    var name = faker.name().title();
    var document = helper.createDocument(mockedUser);

    var generatedId = new AtomicReference<UUID>();

    mvc.perform(
            post("/documents/" + document.getId() + "/export")
                .contentType("application/json")
                .content(mapper.writeValueAsString(Map.of("name", name))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").isEmpty())
        .andExpect(jsonPath("$.data.name").value(name))
        .andDo(
            result -> {
              var response = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
              generatedId.set(
                  UUID.fromString((String) ((Map<?, ?>) response.get("data")).get("id")));
            });

    verify(exportProducer, times(1)).sendExportCreationTask(generatedId.get().toString());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw DocumentNotFoundException when document not found")
  public void testDocumentNotFound() throws Exception {
    mvc.perform(get("/documents/" + faker.internet().uuid()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.errors.length()").value(1))
        .andExpect(jsonPath("$.errors[0].code").value("DOCUMENT_NOT_FOUND"));
  }
}
