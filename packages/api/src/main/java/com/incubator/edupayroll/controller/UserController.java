package com.incubator.edupayroll.controller;

import com.incubator.edupayroll.dto.user.UserUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    @PutMapping({"", "/"})
    public ResponseEntity<UserUpdateDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity
                .ok()
                .body(userUpdateDTO);
    }

}
