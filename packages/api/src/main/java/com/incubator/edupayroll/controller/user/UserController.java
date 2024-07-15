package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.dto.user.*;
import com.incubator.edupayroll.mapper.user.UserMapper;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
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

    @PostMapping("/change-password")
    public ResponseEntity<Response<UserChangePasswordPayload, UserErrorCode>> changePassword(
            @RequestBody UserChangePasswordInput input) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();
        userService.changePassword(user, input.getCurrentPassword(), input.getNewPassword());

        return ResponseEntity.ok().body(Response.data(new UserChangePasswordPayload(true)).build());
    }

    @PutMapping("/update")
    public ResponseEntity<Response<UserUpdatePayload, UserErrorCode>> updateUser(@RequestBody UserNameUpdateInput input) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();
        userService.changeName(user, input.getFirstName(), input.getLastName());

        return ResponseEntity.ok().body(Response.data(new UserUpdatePayload(true)).build());
    }

    @PutMapping("/update/email")
    public ResponseEntity<Response<UserUpdatePayload, UserErrorCode>> updateUser(@RequestBody UserEmailUpdateInput input) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();

        userService.changeMail(user, input.getEmail(), input.getPassword());

        return ResponseEntity.ok().body(Response.data(new UserUpdatePayload(true)).build());
    }

}
