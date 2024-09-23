package com.incubator.edupayroll.service;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.common.exception.AccessDeniedException;
import com.incubator.edupayroll.common.selection.SelectionType;
import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.export.ExportStatus;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.repository.ExportRepository;
import com.incubator.edupayroll.service.export.ExportNotFoundException;
import com.incubator.edupayroll.service.export.ExportService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class ExportServiceTest {
  @Autowired private Faker faker;
  @Autowired private TestHelper helper;
  @Autowired private ExportService exportService;
  @Autowired private ExportRepository exportRepository;

  private UserEntity user;
  private DocumentEntity document;

  @BeforeEach
  public void setup() {
    user = helper.createUser();
    document = helper.createDocument(user);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get export by id")
  public void testGetById() {
    var export = helper.createExport(document);

    var result = exportService.getById(export.getId());

    assertNotNull(result);
    assertEquals(export.getId(), result.getId());
    assertEquals(export.getName(), result.getName());
    assertEquals(export.getStatus(), result.getStatus());
    assertEquals(export.getDocument().getId(), result.getDocument().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get export by id and user")
  public void testGetByIdAndUser() {
    var export = helper.createExport(document);

    var result = exportService.getById(user, export.getId());

    assertNotNull(result);
    assertEquals(export.getId(), result.getId());
    assertEquals(export.getName(), result.getName());
    assertEquals(export.getStatus(), result.getStatus());
    assertEquals(export.getDocument().getId(), result.getDocument().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw AccessDeniedException when getting export by id and user")
  public void testGetByIdAndUserThrowsAccessDeniedException() {
    var export = helper.createExport(document);

    var user2 = helper.createUser();
    assertThrows(AccessDeniedException.class, () -> exportService.getById(user2, export.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw ExportNotFoundException when getting export by id")
  public void testGetByIdThrowsExportNotFoundException() {
    assertThrows(ExportNotFoundException.class, () -> exportService.getById(UUID.randomUUID()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should count exports")
  public void testCount() {
    helper.createExport(document);
    helper.createExport(document);
    helper.createExport(document);

    var result = exportService.count(user, Optional.empty());

    assertEquals(3, result);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should count exports by name")
  public void testCountByName() {
    helper.createExport(document);
    helper.createExport(document);

    var export = helper.createExport(document);

    var result = exportService.count(user, Optional.of(export.getName()));

    assertEquals(1, result);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all exports")
  public void testGetAll() {
    helper.createExport(document);
    helper.createExport(document);

    var export = helper.createExport(document);

    var result = exportService.getAll(user, 10, 0, Optional.empty());

    assertEquals(3, result.size());
    assertEquals(export.getId(), result.getFirst().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all exports by page")
  public void testGetAllByPage() {
    var last = helper.createExport(document);

    for (int i = 0; i < 3; i++) {
      helper.createExport(document);
    }

    var result = exportService.getAll(user, 2, 2, Optional.empty());

    assertEquals(2, result.size());
    assertEquals(last.getId(), result.getLast().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all exports by name")
  public void testGetAllByName() {
    helper.createExport(document);
    helper.createExport(document);

    var export = helper.createExport(document);

    var result = exportService.getAll(user, 10, 0, Optional.of(export.getName()));

    assertEquals(1, result.size());
    assertEquals(export.getId(), result.getFirst().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all exports by document")
  public void testGetAllByDocument() {
    var anotherDocument = helper.createDocument(user);
    var export = helper.createExport(document);
    var anotherExport = helper.createExport(anotherDocument);

    var result = exportService.getAllByDocument(document);

    assertEquals(1, result.size());
    assertEquals(export.getId(), result.getFirst().getId());

    result = exportService.getAllByDocument(anotherDocument);

    assertEquals(1, result.size());
    assertEquals(anotherExport.getId(), result.getFirst().getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create export")
  public void testCreate() {
    var name = faker.name().title();
    var result = exportService.create(document, name);

    assertNotNull(result);

    assertNull(result.getPath());
    assertEquals(name, result.getName());
    assertEquals(document, result.getDocument());
    assertEquals(ExportStatus.PENDING, result.getStatus());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update export status")
  public void testUpdateStatus() {
    var export = helper.createExport(document);

    var status = ExportStatus.COMPLETED;
    var updated = exportService.updateStatus(export, status);

    assertNotNull(updated);
    assertEquals(status, updated.getStatus());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update export name")
  public void testUpdateName() {
    var export = helper.createExport(document);

    var name = faker.name().title();
    var updated = exportService.updateName(export, name);

    assertNotNull(updated);
    assertEquals(name, updated.getName());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update export path")
  public void testUpdatePath() {
    var export = helper.createExport(document);

    var path = faker.internet().url();
    var updated = exportService.updatePath(export, path);

    assertNotNull(updated);
    assertEquals(path, updated.getPath());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove export")
  public void testRemove() {
    var export = helper.createExport(document);

    exportService.remove(export);

    assertFalse(exportRepository.existsById(export.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove exports by document")
  public void testRemoveByDocument() {
    var anotherDocument = helper.createDocument(user);

    var export1 = helper.createExport(document);
    var export2 = helper.createExport(document);
    var export3 = helper.createExport(anotherDocument);
    var export4 = helper.createExport(anotherDocument);

    exportService.removeAllByDocument(document);

    assertTrue(exportRepository.existsById(export3.getId()));
    assertTrue(exportRepository.existsById(export4.getId()));

    assertFalse(exportRepository.existsById(export1.getId()));
    assertFalse(exportRepository.existsById(export2.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove exports by user and ids include")
  public void testBulkRemoveInclude() {
    var export1 = helper.createExport(document);
    var export2 = helper.createExport(document);
    var export3 = helper.createExport(document);

    exportService.bulkRemove(
        user, SelectionType.INCLUDE, List.of(export1.getId(), export2.getId()));

    assertTrue(exportRepository.existsById(export3.getId()));

    assertFalse(exportRepository.existsById(export1.getId()));
    assertFalse(exportRepository.existsById(export2.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove exports by user and ids exclude")
  public void testBulkRemoveExclude() {
    var export1 = helper.createExport(document);
    var export2 = helper.createExport(document);
    var export3 = helper.createExport(document);

    exportService.bulkRemove(
        user, SelectionType.EXCLUDE, List.of(export1.getId(), export2.getId()));

    assertTrue(exportRepository.existsById(export1.getId()));
    assertTrue(exportRepository.existsById(export2.getId()));

    assertFalse(exportRepository.existsById(export3.getId()));
  }
}
