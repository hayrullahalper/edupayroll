package com.incubator.edupayroll.service;

import static org.junit.jupiter.api.Assertions.*;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordType;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.entity.user.UserEntity;
import com.incubator.edupayroll.helper.TestHelper;
import com.incubator.edupayroll.service.record.RecordHoursTooLongException;
import com.incubator.edupayroll.service.record.RecordNotFoundException;
import com.incubator.edupayroll.service.record.RecordService;
import com.incubator.edupayroll.service.record.RecordStructureCorrupted;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
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
public class RecordServiceTest {
  @Autowired private TestHelper helper;
  @Autowired private RecordService recordService;

  private UserEntity user;
  private TeacherEntity teacher;
  private DocumentEntity document;
  private final RecordType type = RecordType.Course101;
  private final List<Integer> hours = List.of(0, 0, 0, 0);

  @BeforeEach
  public void setup() {
    user = helper.createUser();
    teacher = helper.createTeacher(user);
    document = helper.createDocument(user);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record correctly when no record exists")
  public void testRecordServiceCreate() {
    var type = RecordType.Course101;

    var record = recordService.create(document, teacher, type, hours, Optional.empty());

    assertNotNull(record);

    assertTrue(record.isHead());
    assertEquals(record.getType(), type);
    assertEquals(record.getHours(), hours);
    assertEquals(record.getTeacher().getId(), teacher.getId());
    assertEquals(record.getDocument().getId(), document.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record correctly to the end")
  public void testRecordServiceCreateAtEnd() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    assertNotNull(record1);
    assertNotNull(record2);
    assertNotNull(record3);

    assertNull(record3.getNext());
    assertEquals(record2.getNext().getId(), record3.getId());
    assertEquals(record1.getNext().getId(), record2.getId());

    assertTrue(record1.isHead());
    assertFalse(record2.isHead());
    assertFalse(record3.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record correctly to the begin")
  public void testRecordServiceCreateAtBegin() {
    var record3 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());

    assertNotNull(record1);
    assertNotNull(record2);
    assertNotNull(record3);

    assertNull(record3.getNext());
    assertEquals(record2.getNext().getId(), record3.getId());
    assertEquals(record1.getNext().getId(), record2.getId());

    assertTrue(record1.isHead());
    assertFalse(record2.isHead());
    assertFalse(record3.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record correctly in middle correctly")
  public void testRecordServiceCreateInMiddle() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));

    assertNotNull(record1);
    assertNotNull(record2);
    assertNotNull(record3);

    assertNull(record3.getNext());
    assertEquals(record2.getNext().getId(), record3.getId());
    assertEquals(record1.getNext().getId(), record2.getId());

    assertTrue(record1.isHead());
    assertFalse(record2.isHead());
    assertFalse(record3.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get all records by document correctly")
  public void testRecordServiceGetAll() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    var records = recordService.getAllByDocument(document);

    assertEquals(records.size(), 3);
    assertEquals(records.get(0).getId(), record1.getId());
    assertEquals(records.get(1).getId(), record2.getId());
    assertEquals(records.get(2).getId(), record3.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should get record by id correctly")
  public void testRecordServiceGetById() {
    var record = recordService.create(document, teacher, type, hours, Optional.empty());
    var found = recordService.getById(user, record.getId());

    assertNotNull(found);
    assertEquals(found.getId(), record.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update basic record information correctly")
  public void testRecordServiceUpdateBasic() {
    var record = recordService.create(document, teacher, type, hours, Optional.empty());
    var newType = RecordType.Course102;
    var newTeacher = helper.createTeacher(user);

    var updated = recordService.updateInformation(record, newTeacher, newType);

    assertEquals(updated.getType(), newType);
    assertEquals(updated.getTeacher().getId(), newTeacher.getId());

    assertNotEquals(updated.getType(), type);
    assertNotEquals(updated.getTeacher().getId(), teacher.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update hours correctly")
  public void testRecordServiceUpdateHours() {
    var record = recordService.create(document, teacher, type, hours, Optional.empty());
    var newHours = List.of(1, 2, 3, 4);

    var updated = recordService.updateHours(record, newHours);

    assertEquals(updated.getHours(), newHours);
    assertNotEquals(updated.getHours(), hours);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update previous record correctly when updating middle record")
  public void testRecordServiceUpdatePrevious() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    record3 = recordService.updatePrevious(record3, Optional.of(record1));

    record1 = recordService.getById(user, record1.getId());
    record2 = recordService.getById(user, record2.getId());

    assertNull(record2.getNext());
    assertEquals(record3.getNext().getId(), record2.getId());
    assertEquals(record1.getNext().getId(), record3.getId());

    assertTrue(record1.isHead());
    assertFalse(record3.isHead());
    assertFalse(record2.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update previous record correctly when updating middle record with tail")
  public void testRecordServiceUpdatePreviousWithTail() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));
    var record4 = recordService.create(document, teacher, type, hours, Optional.of(record3));

    record3 = recordService.updatePrevious(record3, Optional.of(record1));

    record2 = recordService.getById(user, record2.getId());
    record1 = recordService.getById(user, record1.getId());

    assertEquals(record2.getNext().getId(), record4.getId());
    assertEquals(record3.getNext().getId(), record2.getId());
    assertEquals(record1.getNext().getId(), record3.getId());

    assertTrue(record1.isHead());
    assertFalse(record3.isHead());
    assertFalse(record2.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should not update previous record when record is already in correct position")
  public void testRecordServiceUpdatePreviousAlreadyCorrect() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    record3 = recordService.updatePrevious(record3, Optional.of(record2));

    record1 = recordService.getById(user, record1.getId());
    record2 = recordService.getById(user, record2.getId());

    assertNull(record3.getNext());
    assertEquals(record1.getNext().getId(), record2.getId());
    assertEquals(record2.getNext().getId(), record3.getId());

    assertTrue(record1.isHead());
    assertFalse(record3.isHead());
    assertFalse(record2.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should update previous record correctly when updating head record")
  public void testRecordServiceUpdatePreviousHead() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    record3 = recordService.updatePrevious(record3, Optional.empty());

    record1 = recordService.getById(user, record1.getId());
    record2 = recordService.getById(user, record2.getId());

    assertNull(record2.getNext());
    assertEquals(record1.getNext().getId(), record2.getId());
    assertEquals(record3.getNext().getId(), record1.getId());

    assertTrue(record3.isHead());
    assertFalse(record1.isHead());
    assertFalse(record2.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove record correctly when record is in the middle")
  public void testRecordServiceRemoveMiddle() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    recordService.remove(record2);

    record1 = recordService.getById(user, record1.getId());
    record3 = recordService.getById(user, record3.getId());

    assertNull(record3.getNext());
    assertEquals(record1.getNext().getId(), record3.getId());

    assertTrue(record1.isHead());
    assertFalse(record3.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove record correctly when record is at the end")
  public void testRecordServiceRemoveEnd() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));

    recordService.remove(record2);
    record1 = recordService.getById(user, record1.getId());

    assertNull(record1.getNext());
    assertTrue(record1.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove record correctly when record is at the beginning")
  public void testRecordServiceRemoveBegin() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));

    recordService.remove(record1);
    record2 = recordService.getById(user, record2.getId());

    assertNull(record2.getNext());
    assertTrue(record2.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should remove all by document correctly")
  public void testRecordServiceRemoveAll() {
    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    var record2 = recordService.create(document, teacher, type, hours, Optional.of(record1));
    var record3 = recordService.create(document, teacher, type, hours, Optional.of(record2));

    recordService.removeAllByDocument(document);

    assertThrows(RecordNotFoundException.class, () -> recordService.getById(user, record1.getId()));
    assertThrows(RecordNotFoundException.class, () -> recordService.getById(user, record2.getId()));
    assertThrows(RecordNotFoundException.class, () -> recordService.getById(user, record3.getId()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw RecordHoursTooLong when hours are too long")
  public void testRecordServiceCreateHoursTooLong() {
    var tooLongHours = new ArrayList<Integer>();

    for (int i = 0; i < 32; i++) {
      tooLongHours.add(0);
    }

    assertThrows(
        RecordHoursTooLongException.class,
        () -> recordService.create(document, teacher, type, tooLongHours, Optional.empty()));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw RecordStructureCorrupted when record is not in the same document")
  public void testRecordServiceGetAllByDocumentCorrupted() {
    var anotherDocument = helper.createDocument(user);

    var record1 = recordService.create(document, teacher, type, hours, Optional.empty());
    recordService.create(anotherDocument, teacher, type, hours, Optional.of(record1));

    assertThrows(RecordStructureCorrupted.class, () -> recordService.getAllByDocument(document));
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should throw RecordNotFoundException when record is not found")
  public void testRecordServiceGetByIdNotFound() {
    assertThrows(
        RecordNotFoundException.class, () -> recordService.getById(user, UUID.randomUUID()));
  }
}
