package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.service.auth.InvalidCredentialsException;
import com.incubator.edupayroll.service.user.UserAlreadyRegisteredException;
import com.incubator.edupayroll.service.user.UserNotFoundException;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler {

    @ExceptionHandler({UserAlreadyRegisteredException.class})
    public ResponseEntity<Response<?, ?, ResponseError<AuthErrorCode>>> handleUserAlreadyRegisteredException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Response.error(new ResponseError<>(AuthErrorCode.USER_ALREADY_REGISTERED, "User already registered")));
    }

    @ExceptionHandler({UserNotFoundException.class, InvalidCredentialsException.class})
    public ResponseEntity<Response<?, ?, ResponseError<AuthErrorCode>>> handleInvalidCredentialsException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Response.error(new ResponseError<>(AuthErrorCode.INVALID_CREDENTIALS, "Invalid credentials")));
    }
}
