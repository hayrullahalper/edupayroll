package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.dto.auth.*;
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

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Response<TokenPayload, AuthErrorCode>> login(
            @RequestBody LoginInput input) {
        Validation.validate(input);

        var user = authService.login(input.getEmail(), input.getPassword());
        var token = generateUserToken(user.getEmail());

        return ResponseEntity.ok().body(Response.data(new TokenPayload(token)).build());
    }

    @PostMapping("/register")
    public ResponseEntity<Response<RegisterPayload, AuthErrorCode>> register(
            @RequestBody RegisterInput input) {
        Validation.validate(input);

        var token = generateUserToken(input.email);

        authService.register(input.email, token);

        return ResponseEntity.ok().body(Response.data(new RegisterPayload(true)).build());
    }

    @PostMapping("/register/complete")
    public ResponseEntity<Response<TokenPayload, AuthErrorCode>> register(
            @RequestBody RegisterCompleteInput input) {
        Validation.validate(input);

        var context = tokenService.decode(input.getToken());
        var email = context.get("email").asString();

        var user = authService.completeRegister(
                input.getName(),
                email,
                input.getTitle(),
                input.getPassword(),
                input.getSchoolName(),
                input.getPrincipalName());

        var token = generateUserToken(user.getEmail());

        return ResponseEntity.ok().body(Response.data(new TokenPayload(token)).build());
    }

    private String generateUserToken(String email) {
        return tokenService.encode(new HashMap<>(Map.of("email", email)));
    }

}
