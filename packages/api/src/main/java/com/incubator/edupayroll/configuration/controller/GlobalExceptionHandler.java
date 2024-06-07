package com.incubator.edupayroll.configuration.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.response.ResponseError;
import com.incubator.edupayroll.util.validation.InvalidConstraintsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    enum ErrorCode {
        INVALID_JSON,
        INVALID_TYPE,
        UNKNOWN_PROPERTY,
        INVALID_CONSTRAINTS,
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Response<?, ?, ResponseError<ErrorCode>>> handleJsonParseException(JsonParseException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(new ResponseError<>(ErrorCode.INVALID_JSON, "Invalid JSON: " + ex.getOriginalMessage())));
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Response<?, ?, ResponseError<ErrorCode>>> handleMismatchedInputException(MismatchedInputException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(new ResponseError<>(ErrorCode.INVALID_TYPE, "Invalid type: " + ex.getTargetType().getSimpleName())));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Response<?, ?, ResponseError<ErrorCode>>> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(new ResponseError<>(ErrorCode.INVALID_TYPE, "Invalid type: " + ex.getTargetType().getSimpleName())));
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<Response<?, ?, ResponseError<ErrorCode>>> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(new ResponseError<>(ErrorCode.UNKNOWN_PROPERTY, "Unknown property: " + ex.getPropertyName())));
    }

    @ExceptionHandler({InvalidConstraintsException.class})
    public ResponseEntity<Response<?, ?, ResponseError<ErrorCode>>> handleInvalidConstraintsException(InvalidConstraintsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.errors(e.getViolations().stream().map(violation -> new ResponseError<>(ErrorCode.INVALID_CONSTRAINTS, violation)).toList()));
    }
}