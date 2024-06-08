package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.service.auth.InvalidCredentialsException;
import com.incubator.edupayroll.service.user.UserAlreadyRegisteredException;
import com.incubator.edupayroll.service.user.UserNotFoundException;
import com.incubator.edupayroll.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler {

    @ExceptionHandler({UserAlreadyRegisteredException.class})
    public ResponseEntity<Response<?, AuthErrorCode>> handleUserAlreadyRegisteredException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Response
                        .error(AuthErrorCode.USER_ALREADY_REGISTERED, "User already registered")
                        .build()
                );
    }

    @ExceptionHandler({UserNotFoundException.class, InvalidCredentialsException.class})
    public ResponseEntity<Response<?, AuthErrorCode>> handleInvalidCredentialsException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Response
                        .error(AuthErrorCode.INVALID_CREDENTIALS, "Invalid credentials")
                        .build()
                );
    }
}
