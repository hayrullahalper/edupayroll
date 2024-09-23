package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.common.response.Response;
import com.incubator.edupayroll.service.school.SchoolNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = SchoolController.class)
public class SchoolExceptionHandler {
  @ExceptionHandler({SchoolNotFoundException.class})
  public ResponseEntity<Response<?, SchoolErrorCode>> handleSchoolNotFoundException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Response.error(SchoolErrorCode.SCHOOL_NOT_FOUND, "School not found").build());
  }
}
