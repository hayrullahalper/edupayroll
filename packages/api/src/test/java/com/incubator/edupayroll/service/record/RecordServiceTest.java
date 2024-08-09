package com.incubator.edupayroll.service.record;

import static org.junit.jupiter.api.Assertions.*;

import com.incubator.edupayroll.entity.document.DocumentEntity;
import com.incubator.edupayroll.entity.record.RecordType;
import com.incubator.edupayroll.entity.teacher.TeacherEntity;
import com.incubator.edupayroll.helper.TestHelper;
import jakarta.transaction.Transactional;
import java.util.Optional;
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

  private TeacherEntity teacher;
  private DocumentEntity document;
  private final String information = "[1,1,1,1]";
  private final RecordType type = RecordType.Course101;

  @BeforeEach
  public void setup() {
    var user = helper.createUser();
    teacher = helper.createTeacher(user);
    document = helper.createDocument(user);
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record correctly when no record exists")
  public void testRecordServiceCreate() {
    var type = RecordType.Course101;

    var record = recordService.create(document, teacher, type, information, Optional.empty());

    assertNotNull(record);

    assertTrue(record.isHead());
    assertEquals(record.getType(), type);
    assertEquals(record.getInformation(), information);
    assertEquals(record.getTeacher().getId(), teacher.getId());
    assertEquals(record.getDocument().getId(), document.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record successfully to the end")
  public void testRecordServiceCreateAtEnd() {
    var previous = recordService.create(document, teacher, type, information, Optional.empty());
    var record = recordService.create(document, teacher, type, information, Optional.of(previous));

    assertNotNull(record);
    assertNotNull(previous);

    assertEquals(record.getType(), type);
    assertEquals(record.getInformation(), information);
    assertEquals(record.getTeacher().getId(), teacher.getId());
    assertEquals(record.getDocument().getId(), document.getId());

    assertNull(record.getNext());
    assertFalse(record.isHead());
    assertTrue(previous.isHead());

    assertEquals(previous.getNext().getId(), record.getId());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record successfully to the begin")
  public void testRecordServiceCreateAtBegin() {
    var user = helper.createUser();
    var teacher = helper.createTeacher(user);
    var document = helper.createDocument(user);

    var type = RecordType.Course101;

    var information = "[1,1,1,1]";

    var prev = recordService.create(document, teacher, type, information, Optional.empty());

    var record = recordService.create(document, teacher, type, information, Optional.empty());

    assertNotNull(prev);
    assertNotNull(record);

    assertEquals(record.getNext().getId(), prev.getId());

    assertTrue(record.isHead());
    assertFalse(prev.isHead());
  }

  @Test
  @Transactional
  @Rollback
  @DisplayName("should create record in middle correctly")
  public void testRecordServiceCreateInMiddle() {
    var previous = recordService.create(document, teacher, type, information, Optional.empty());
    var next = recordService.create(document, teacher, type, information, Optional.of(previous));

    var middle = recordService.create(document, teacher, type, information, Optional.of(previous));
    assertNotNull(previous);
    assertNotNull(middle);
    assertNotNull(next);

    assertEquals(middle.getNext().getId(), next.getId());
    assertEquals(previous.getNext().getId(), middle.getId());

    assertFalse(middle.isHead());
    assertFalse(next.isHead());
    assertTrue(previous.isHead());
  }

  //  @Test
  //  @Transactional
  //  @Rollback
  //  @DisplayName("should create record at last correctly")
  //  public void testRecordServiceCreateAtLast() {
  //    var user = helper.createUser();
  //    var teacher = helper.createTeacher(user);
  //    var document = helper.createDocument(user);
  //
  //    var type = RecordType.Course118;
  //    var information = "[1,1,1,1]";
  //
  //    var previous =
  //        recordService.create(
  //            document, teacher, type, information, Optional.empty(), Optional.empty());
  //
  //    var record =
  //        recordService.create(
  //            document, teacher, type, information, Optional.empty(), Optional.of(previous));
  //
  //    assertNotNull(record);
  //
  //    assertEquals(record.getType(), type);
  //    assertEquals(record.getInformation(), information);
  //    assertEquals(record.getTeacher().getId(), teacher.getId());
  //    assertEquals(record.getDocument().getId(), document.getId());
  //
  //    assertEquals(record.getPrevious().getId(), previous.getId());
  //  }
}
