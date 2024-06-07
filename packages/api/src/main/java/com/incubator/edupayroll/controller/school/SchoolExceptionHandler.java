package com.incubator.edupayroll.controller.school;

import com.incubator.edupayroll.service.school.SchoolNotFoundException;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = SchoolController.class)
public class SchoolExceptionHandler {
    @ExceptionHandler({SchoolNotFoundException.class})
    public ResponseEntity<Response<?, ?, ResponseError<SchoolErrorCode>>> handleSchoolNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Response.error(new ResponseError<>(SchoolErrorCode.SCHOOL_NOT_FOUND, "School not found")));
    }
}
