package com.incubator.edupayroll.configuration.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.incubator.edupayroll.util.exception.AccessDeniedException;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.InvalidConstraintsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Response<?, GlobalErrorCode>> handleJsonParseException(JsonParseException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response
                        .error(GlobalErrorCode.INVALID_JSON, "Invalid JSON: " + ex.getOriginalMessage())
                        .build()
                );
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<Response<?, GlobalErrorCode>> handleMismatchedInputException(MismatchedInputException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response
                        .error(GlobalErrorCode.INVALID_TYPE, "Invalid type: " + ex.getTargetType().getSimpleName())
                        .build()
                );
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Response<?, GlobalErrorCode>> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response
                        .error(GlobalErrorCode.INVALID_TYPE, "Invalid type: " + ex.getTargetType().getSimpleName())
                        .build()
                );
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<Response<?, GlobalErrorCode>> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response
                        .error(GlobalErrorCode.UNKNOWN_PROPERTY, "Unknown property: " + ex.getPropertyName())
                        .build()
                );
    }

    @ExceptionHandler({InvalidConstraintsException.class})
    public ResponseEntity<Response<?, GlobalErrorCode>> handleInvalidConstraintsException(InvalidConstraintsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response
                        .error(GlobalErrorCode.INVALID_CONSTRAINTS, e.getViolations())
                        .build()
                );
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Response<?, GlobalErrorCode>> handleAccessDeniedException() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Response
                        .error(GlobalErrorCode.ACCESS_DENIED, "You do not have permission to access this resource")
                        .build()
                );
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<Response<?, GlobalErrorCode>> handleNoResourceFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response
                        .error(GlobalErrorCode.NO_RESOURCE_FOUND, "Resource not found")
                        .build()
                );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<?, GlobalErrorCode>> handleException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response
                        .error(GlobalErrorCode.INTERNAL_SERVER_ERROR, "Internal server error")
                        .build()
                );
    }
}