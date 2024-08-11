package com.incubator.edupayroll.controller.document;

import com.incubator.edupayroll.service.document.DocumentNotFoundException;
import com.incubator.edupayroll.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = DocumentController.class)
public class DocumentExceptionHandler {
  @ExceptionHandler({DocumentNotFoundException.class})
  public ResponseEntity<Response<?, DocumentErrorCode>> handleDocumentNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(DocumentErrorCode.DOCUMENT_NOT_FOUND, "Document not found").build());
  }
}
