package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.service.user.UserExistsByEmailException;
import com.incubator.edupayroll.service.user.UserNotFoundException;
import com.incubator.edupayroll.service.user.UserPasswordMismatchException;
import com.incubator.edupayroll.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {

  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<Response<?, UserErrorCode>> handleInvalidCredentialsException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Response.error(UserErrorCode.USER_NOT_FOUND, "User not found").build());
  }

  @ExceptionHandler({UserPasswordMismatchException.class})
  public ResponseEntity<Response<?, UserErrorCode>> handleUserPasswordMismatchException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Response.error(UserErrorCode.PASSWORD_MISMATCH, "Password mismatch").build());
  }

  @ExceptionHandler({UserExistsByEmailException.class})
  public ResponseEntity<Response<?, UserErrorCode>> handleUserExistsByEmailException() {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Response.error(UserErrorCode.USER_EMAIL_EXISTS, "User exists by mail").build());
  }
}
