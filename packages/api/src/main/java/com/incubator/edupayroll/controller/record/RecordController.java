package com.incubator.edupayroll.controller.record;

import com.incubator.edupayroll.dto.record.*;
import com.incubator.edupayroll.dto.record.Record;
import com.incubator.edupayroll.mapper.record.RecordMapper;
import com.incubator.edupayroll.service.document.DocumentService;
import com.incubator.edupayroll.service.record.RecordService;
import com.incubator.edupayroll.service.teacher.TeacherService;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import java.util.Optional;
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

  @PostMapping("")
  public ResponseEntity<Response<Record, RecordErrorCode>> create(
      @RequestBody RecordCreateInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var teacher = teacherService.getById(user, input.getTeacherId());
    var document = documentService.getById(user, input.getDocumentId());

    var previous =
        Optional.ofNullable(input.getPreviousId()).map(id -> recordService.getById(user, id));

    var record =
        recordService.create(document, teacher, input.getType(), input.getHours(), previous);

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(record)).build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<Record, RecordErrorCode>> update(
      @PathVariable("id") UUID id, @RequestBody RecordUpdateInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(user, id);
    var teacher = teacherService.getById(user, input.getTeacherId());

    var updated = recordService.updateInformation(record, teacher, input.getType());

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(updated)).build());
  }

  @PutMapping("/{id}/hours")
  public ResponseEntity<Response<Record, RecordErrorCode>> updateHours(
      @PathVariable("id") UUID id, @RequestBody RecordUpdateHoursInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(user, id);

    var updated = recordService.updateHours(record, input.getHours());

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(updated)).build());
  }

  @PutMapping("/{id}/previous")
  public ResponseEntity<Response<Record, RecordErrorCode>> updateOrder(
      @PathVariable("id") UUID id, @RequestBody RecordUpdateOrderInput input) {
    Validation.validate(input);

    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(user, id);
    var previous =
        Optional.ofNullable(input.getPreviousId())
            .map((previousId) -> recordService.getById(user, previousId));

    var updated = recordService.updatePrevious(record, previous);

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(updated)).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<Record, RecordErrorCode>> delete(@PathVariable("id") UUID id) {
    var user = userService.getAuthenticatedUser();
    var record = recordService.getById(user, id);

    recordService.remove(record);

    return ResponseEntity.ok().body(Response.data(RecordMapper.toDTO(record)).build());
  }
}
