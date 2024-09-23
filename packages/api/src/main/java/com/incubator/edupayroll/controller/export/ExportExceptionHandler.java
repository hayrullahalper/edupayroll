package com.incubator.edupayroll.controller.export;

import com.incubator.edupayroll.service.export.ExportNotFoundException;
import com.incubator.edupayroll.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = ExportController.class)
public class ExportExceptionHandler {
  @ExceptionHandler({ExportNotFoundException.class})
  public ResponseEntity<Response<?, ExportErrorCode>> handleExportNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(ExportErrorCode.EXPORT_NOT_FOUND, "Export not found").build());
  }
}
