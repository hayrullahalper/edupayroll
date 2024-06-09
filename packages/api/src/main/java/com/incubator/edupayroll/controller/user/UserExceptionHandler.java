package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.service.user.UserNotFoundException;
import com.incubator.edupayroll.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Response<?, UserErrorCode>> handleInvalidCredentialsException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response
                        .error(UserErrorCode.USER_NOT_FOUND, "User not found")
                        .build()
                );
    }
}