package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.dto.auth.LoginDTO;
import com.incubator.edupayroll.dto.auth.RegisterDTO;
import com.incubator.edupayroll.dto.auth.TokenDTO;
import com.incubator.edupayroll.service.auth.AuthService;
import com.incubator.edupayroll.service.token.TokenService;
import com.incubator.edupayroll.util.response.Response;
import com.incubator.edupayroll.util.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<TokenDTO, ?, ?>> login(@RequestBody LoginDTO request) {
        Validation.validate(request);

        var user = authService.login(request.getEmail(), request.getPassword());
        var token = tokenService.encode(user);

        return ResponseEntity
                .ok()
                .body(Response.data(new TokenDTO(token)));
    }

    @PostMapping("/register")
    public ResponseEntity<Response<TokenDTO, ?, ?>> register(@RequestBody RegisterDTO request) {
        Validation.validate(request);

        var user = authService.register(
                request.getName(),
                request.getEmail(),
                request.getTitle(),
                request.getPassword(),
                request.getSchoolName(),
                request.getPrincipalName()
        );

        var token = tokenService.encode(user);

        return ResponseEntity
                .ok()
                .body(Response.data(new TokenDTO(token)));
    }

}
