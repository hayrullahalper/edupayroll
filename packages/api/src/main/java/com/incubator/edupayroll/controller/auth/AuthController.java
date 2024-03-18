package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.dto.auth.LoginDTO;
import com.incubator.edupayroll.dto.auth.RegisterDTO;
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
}
