package com.incubator.edupayroll.configuration.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.incubator.edupayroll.common.exception.AccessDeniedException;
import com.incubator.edupayroll.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({JsonParseException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleJsonParseException(
      JsonParseException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(GlobalErrorCode.INVALID_JSON, "Invalid JSON: " + e.getOriginalMessage())
                .build());
  }

  @ExceptionHandler({MismatchedInputException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleMismatchedInputException(
      MismatchedInputException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(
                    GlobalErrorCode.INVALID_TYPE,
                    "Invalid type: " + e.getTargetType().getSimpleName())
                .build());
  }

  @ExceptionHandler({InvalidFormatException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleInvalidFormatException(
      InvalidFormatException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(
                    GlobalErrorCode.INVALID_TYPE,
                    "Invalid type: " + e.getTargetType().getSimpleName())
                .build());
  }

  @ExceptionHandler({UnrecognizedPropertyException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleUnrecognizedPropertyException(
      UnrecognizedPropertyException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(
                    GlobalErrorCode.UNKNOWN_PROPERTY, "Unknown property: " + e.getPropertyName())
                .build());
  }

  @ExceptionHandler({InvalidConstraintsException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleInvalidConstraintsException(
      InvalidConstraintsException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Response.error(GlobalErrorCode.INVALID_CONSTRAINTS, e.getViolations()).build());
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleAccessDeniedException() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            Response.error(
                    GlobalErrorCode.ACCESS_DENIED,
                    "You do not have permission to access this resource")
                .build());
  }

  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleNoResourceFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(GlobalErrorCode.NO_RESOURCE_FOUND, "Resource not found").build());
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleException() {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(
                    GlobalErrorCode.INVALID_REQUEST_BODY, "Request body is missing or invalid")
                .build());
  }

  @ExceptionHandler({MissingServletRequestParameterException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Response.error(
                    GlobalErrorCode.MISSING_REQUEST_PARAMETER,
                    "Request parameter is missing: " + e.getParameterName())
                .build());
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<Response<?, GlobalErrorCode>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(
            Response.error(
                    GlobalErrorCode.METHOD_NOT_ALLOWED, "Method not allowed: " + e.getMethod())
                .build());
  }
}
