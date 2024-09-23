package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.dto.user.*;
import com.incubator.edupayroll.mapper.user.UserMapper;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.common.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("")
  public ResponseEntity<Response<User, UserErrorCode>> getUser() {
    var user = userService.getAuthenticatedUser();

    return ResponseEntity.ok().body(Response.data(UserMapper.toDTO(user)).build());
  }

  @PutMapping("/password")
  public ResponseEntity<Response<UserUpdatePasswordPayload, UserErrorCode>> updatePassword(
      @Valid @RequestBody UserUpdatePasswordInput input) {
    var user = userService.getAuthenticatedUser();
    userService.changePassword(user, input.getCurrentPassword(), input.getNewPassword());

    return ResponseEntity.ok().body(Response.data(new UserUpdatePasswordPayload(true)).build());
  }

  @PutMapping("/")
  public ResponseEntity<Response<User, UserErrorCode>> updateName(
      @Valid @RequestBody UserUpdateNameInput input) {
    var user = userService.getAuthenticatedUser();
    var updatedUser = userService.changeName(user, input.getFirstName(), input.getLastName());

    return ResponseEntity.ok().body(Response.data(UserMapper.toDTO(updatedUser)).build());
  }

  @PutMapping("/email")
  public ResponseEntity<Response<User, UserErrorCode>> updateEmail(
      @Valid @RequestBody UserUpdateEmailInput input) {
    var user = userService.getAuthenticatedUser();
    var updatedUser = userService.changeEmail(user, input.getEmail(), input.getPassword());

    return ResponseEntity.ok().body(Response.data(UserMapper.toDTO(updatedUser)).build());
  }
}
