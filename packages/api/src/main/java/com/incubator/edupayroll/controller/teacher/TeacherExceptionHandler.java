package com.incubator.edupayroll.controller.teacher;

import com.incubator.edupayroll.common.response.Response;
import com.incubator.edupayroll.service.teacher.TeacherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = TeacherController.class)
public class TeacherExceptionHandler {

  @ExceptionHandler({TeacherNotFoundException.class})
  public ResponseEntity<Response<?, TeacherErrorCode>> handleTeacherNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(TeacherErrorCode.TEACHER_NOT_FOUND, "Teacher not found").build());
  }
}
