package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.controller.auth.AuthErrorCode;
import com.incubator.edupayroll.dto.user.UserChangePasswordInput;
import com.incubator.edupayroll.dto.user.User;
import com.incubator.edupayroll.dto.user.UserChangePasswordPayload;
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
    public ResponseEntity<Response<UserChangePasswordPayload, AuthErrorCode>> changePassword(
            @RequestBody UserChangePasswordInput input) {
        Validation.validate(input);

        var user = userService.getAuthenticatedUser();
        userService.changePassword(user, input.getCurrentPassword(), input.getNewPassword());

        return ResponseEntity.ok().body(Response.data(new UserChangePasswordPayload(true)).build());
    }

}
