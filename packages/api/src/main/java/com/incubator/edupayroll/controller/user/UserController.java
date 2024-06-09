package com.incubator.edupayroll.controller.user;

import com.incubator.edupayroll.dto.user.User;
import com.incubator.edupayroll.mapper.user.UserMapper;
import com.incubator.edupayroll.service.user.UserService;
import com.incubator.edupayroll.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Response<User, UserErrorCode>> get() {
        var user = userService.getAuthenticatedUser();

        return ResponseEntity
                .ok()
                .body(Response
                        .data(UserMapper.toDTO(user))
                        .build()
                );
    }
}
