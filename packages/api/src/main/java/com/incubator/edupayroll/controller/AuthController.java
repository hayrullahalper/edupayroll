package com.incubator.edupayroll.controller;

import com.incubator.edupayroll.dto.auth.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity
                .ok()
                .body(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity
                .ok()
                .body(registerDTO);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordDTO> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity
                .ok()
                .body(resetPasswordDTO);
    }

    @PostMapping("/reset-password/complete")
    public ResponseEntity<ResetPasswordCompleteDTO> resetPasswordComplete(@RequestBody ResetPasswordCompleteDTO resetPasswordCompleteDTO) {
        return ResponseEntity
                .ok()
                .body(resetPasswordCompleteDTO);
    }

    @PostMapping("/update-password")
    public ResponseEntity<UpdatePasswordDTO> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return ResponseEntity
                .ok()
                .body(updatePasswordDTO);
    }

}
