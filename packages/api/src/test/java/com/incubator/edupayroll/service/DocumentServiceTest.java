package com.incubator.edupayroll.service;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.common.exception.AccessDeniedException;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.RecordRepository;
import com.incubator.edupayroll.service.document.DocumentNotFoundException;
import com.incubator.edupayroll.service.document.DocumentService;
import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class DocumentServiceTest {
  @Autowired private Faker faker;
  @Autowired private TestHelper helper;
  @Autowired private DocumentService documentService;
  @Autowired private RecordRepository recordRepository;

  private UserEntity user;
  private TeacherEntity teacher;

  @BeforeEach
  public void setup() {
    user = helper.createUser();
    teacher = helper.createTeacher(user);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get document by id")
  public void testGetById() {
    var document = helper.createDocument(user);

    var record2 = helper.createRecord(document, teacher);
    var record1 = helper.createRecord(document, teacher);

    var result = documentService.getById(user, document.getId());

    assertNotNull(result);
    assertEquals(document.getId(), result.getId());
    assertEquals(document.getName(), result.getName());
    assertEquals(document.getTime(), result.getTime());
    assertEquals(document.getUser().getId(), result.getUser().getId());

    assertEquals(2, result.getRecords().size());
    assertEquals(record2.getId(), result.getRecords().getLast().getId());
    assertEquals(record1.getId(), result.getRecords().getFirst().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update document")
  public void testUpdate() {
    var document = helper.createDocument(user);

    var name = faker.name().username();

    var date = faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault());
    var time = YearMonth.of(date.getYear(), date.getMonth());

    var result = documentService.update(document, name, time);

    assertNotNull(result);
    assertEquals(document.getId(), result.getId());
    assertEquals(name, result.getName());
    assertEquals(time, result.getTime());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create document")
  public void testCreate() {
    var name = faker.name().username();

    var date = faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault());
    var time = YearMonth.of(date.getYear(), date.getMonth());

    var result = documentService.create(user, name, time);

    assertNotNull(result);
    assertEquals(name, result.getName());
    assertEquals(time, result.getTime());
    assertEquals(user.getId(), result.getUser().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove document")
  public void testRemove() {
    var document = helper.createDocument(user);

    var record2 = helper.createRecord(document, teacher);
    var record1 = helper.createRecord(document, teacher);

    var export1 = helper.createExport(document);
    var export2 = helper.createExport(document);

    documentService.remove(document);

    assertThrows(
        DocumentNotFoundException.class, () -> documentService.getById(user, document.getId()));

    var maybeRecord2 = recordRepository.findById(record2.getId());
    var maybeRecord1 = recordRepository.findById(record1.getId());

    var maybeExport1 = recordRepository.findById(export1.getId());
    var maybeExport2 = recordRepository.findById(export2.getId());

    assertTrue(maybeRecord2.isEmpty());
    assertTrue(maybeRecord1.isEmpty());

    assertTrue(maybeExport1.isEmpty());
    assertTrue(maybeExport2.isEmpty());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw DocumentNotFoundException when document not found")
  public void testGetByIdThrowsDocumentNotFoundException() {
    var document = helper.createDocument(user);

    documentService.remove(document);

    assertThrows(
        DocumentNotFoundException.class, () -> documentService.getById(user, document.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw AccessDeniedException when user is not owner")
  public void testGetByIdThrowsAccessDeniedException() {
    var document = helper.createDocument(user);

    var user2 = helper.createUser();
    assertThrows(
        AccessDeniedException.class, () -> documentService.getById(user2, document.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should count documents")
  public void testCount() {
    helper.createDocument(user);
    helper.createDocument(user);
    helper.createDocument(user);

    var result = documentService.count(user, Optional.empty());

    assertEquals(3, result);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should count documents by name")
  public void testCountByName() {
    helper.createDocument(user);
    helper.createDocument(user);

    var document = helper.createDocument(user);

    var result = documentService.count(user, Optional.of(document.getName()));

    assertEquals(1, result);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all documents")
  public void testGetAll() {
    helper.createDocument(user);
    helper.createDocument(user);

    var document = helper.createDocument(user);

    var result = documentService.getAll(user, 10, 0, Optional.empty());

    assertEquals(3, result.size());
    assertEquals(document.getId(), result.getFirst().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all documents by page")
  public void testGetAllByPage() {
    var last = helper.createDocument(user);

    for (int i = 0; i < 3; i++) {
      helper.createDocument(user);
    }

    var result = documentService.getAll(user, 2, 2, Optional.empty());

    assertEquals(2, result.size());
    assertEquals(last.getId(), result.getLast().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all documents by name")
  public void testGetAllByName() {
    helper.createDocument(user);
    helper.createDocument(user);

    var document = helper.createDocument(user);

    var result = documentService.getAll(user, 10, 0, Optional.of(document.getName()));

    assertEquals(1, result.size());
    assertEquals(document.getId(), result.getFirst().getId());
  }
}
