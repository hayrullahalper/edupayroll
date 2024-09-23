package com.incubator.edupayroll.controller.record;

import com.incubator.edupayroll.common.response.Response;
import com.incubator.edupayroll.service.document.DocumentNotFoundException;
import com.incubator.edupayroll.service.record.RecordHoursTooLongException;
import com.incubator.edupayroll.service.record.RecordNotFoundException;
import com.incubator.edupayroll.service.teacher.TeacherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = RecordController.class)
public class RecordExceptionHandler {

  @ExceptionHandler({TeacherNotFoundException.class})
  public ResponseEntity<Response<?, RecordErrorCode>> handleTeacherNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(RecordErrorCode.TEACHER_NOT_FOUND, "Teacher not found").build());
  }

  @ExceptionHandler({RecordNotFoundException.class})
  public ResponseEntity<Response<?, RecordErrorCode>> handleRecordNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(RecordErrorCode.RECORD_NOT_FOUND, "Record not found").build());
  }

  @ExceptionHandler({DocumentNotFoundException.class})
  public ResponseEntity<Response<?, RecordErrorCode>> handleDocumentNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(RecordErrorCode.DOCUMENT_NOT_FOUND, "Document not found").build());
  }

  @ExceptionHandler({RecordHoursTooLongException.class})
  public ResponseEntity<Response<?, RecordErrorCode>> handleRecordHoursTooLongException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(RecordErrorCode.RECORD_HOURS_TOO_LONG, "Record hours too long").build());
  }
}
