package com.incubator.edupayroll.controller.record;

import com.incubator.edupayroll.dto.record.*;
import com.incubator.edupayroll.dto.record.Record;
import com.incubator.edupayroll.mapper.record.RecordMapper;
import com.incubator.edupayroll.service.document.DocumentService;
import com.incubator.edupayroll.service.record.RecordService;
import com.incubator.edupayroll.service.teacher.TeacherService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/records")
public class RecordController {
  private final UserService userService;
  private final RecordService recordService;
  private final DocumentService documentService;
  private final TeacherService teacherService;

  @Autowired
  public RecordController(
      UserService userService,
      RecordService recordService,
      DocumentService documentService,
      TeacherService teacherService) {
    this.userService = userService;
    this.recordService = recordService;
    this.documentService = documentService;
    this.teacherService = teacherService;
  }

  @GetMapping("/{documentId}")
  public ResponseEntity<Response<List<Record>, RecordErrorCode>> getAll(
      @PathVariable UUID documentId) {
    var user = userService.getAuthenticatedUser();

    var document = documentService.getById(documentId);
    if (!Objects.equals(document.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    List<Record> records = recordService.getRecordsByDocumentId(documentId);

    return ResponseEntity.ok().body(Response.data(records).build());
  }

  @PostMapping("/{documentId}")
  public ResponseEntity<Response<?, RecordErrorCode>> create(
      @PathVariable UUID documentId, @RequestBody RecordCreateDTO input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();

    var teacher = teacherService.getById(user, input.getTeacher());
    if (!Objects.equals(teacher.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    var document = documentService.getById(documentId);
    if (!Objects.equals(document.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    var createdRecord =
        recordService.create(input.getType(), input.getInformation(), teacher, document);

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(createdRecord)).build());
  }

  @PutMapping("/{recordId}/type")
  public ResponseEntity<Response<?, RecordErrorCode>> updateType(
      @PathVariable UUID recordId, @RequestBody RecordUpdateTypeDTO input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(recordId);
    var teacher = record.getTeacher();

    if (!Objects.equals(teacher.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    var updatedRecord = recordService.updateType(record, input.getType());

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(updatedRecord)).build());
  }

  @PutMapping("/{recordId}/teacher")
  public ResponseEntity<Response<?, RecordErrorCode>> updateTeacher(
      @PathVariable UUID recordId, @RequestBody RecordUpdateTeacherDTO input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(recordId);

    var currentTeacher = record.getTeacher();

    if (!Objects.equals(currentTeacher.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    var newTeacher = teacherService.getById(user, input.getTeacher());

    var updatedRecord = recordService.updateTeacher(record, newTeacher);

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(updatedRecord)).build());
  }

  @PutMapping("/{recordId}/information")
  public ResponseEntity<Response<?, RecordErrorCode>> updateInformation(
      @PathVariable UUID recordId, @RequestBody RecordUpdateInformationDTO input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(recordId);
    var teacher = record.getTeacher();

    if (!Objects.equals(teacher.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    var updatedRecord = recordService.updateInformation(record, input.getInformation());

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(updatedRecord)).build());
  }

  @DeleteMapping("/{recordId}")
  public ResponseEntity<Response<?, RecordErrorCode>> deleteRecord(@PathVariable UUID recordId) {
    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(recordId);

    var teacher = record.getTeacher();

    if (!Objects.equals(teacher.getUser().getId(), user.getId()))
      throw new AccessDeniedException("Access denied for user: " + user);

    recordService.delete(record);

    return ResponseEntity.ok().body(Response.data(true).build());
  }
}
