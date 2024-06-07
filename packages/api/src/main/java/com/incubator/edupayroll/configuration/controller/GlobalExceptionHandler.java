package com.incubator.edupayroll.configuration.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.InvalidConstraintsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Response<?, ?, GeneralErrorCode>> handleJsonParseException(JsonParseException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(GeneralErrorCode.INVALID_JSON, "Invalid JSON: " + ex.getOriginalMessage()));
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Response<?, ?, GeneralErrorCode>> handleMismatchedInputException(MismatchedInputException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(GeneralErrorCode.INVALID_TYPE, "Invalid type: " + ex.getTargetType().getSimpleName()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Response<?, ?, GeneralErrorCode>> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(GeneralErrorCode.INVALID_TYPE, "Invalid type: " + ex.getTargetType().getSimpleName()));
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<Response<?, ?, GeneralErrorCode>> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.error(GeneralErrorCode.UNKNOWN_PROPERTY, "Unknown property: " + ex.getPropertyName()));
    }

    @ExceptionHandler({InvalidConstraintsException.class})
    public ResponseEntity<Response<?, ?, GeneralErrorCode>> handleInvalidConstraintsException(InvalidConstraintsException e) {
        var response = new Response<Object, Object, GeneralErrorCode>();
        e.getViolations().forEach(violation -> response.withError(GeneralErrorCode.INVALID_CONSTRAINTS, violation));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}