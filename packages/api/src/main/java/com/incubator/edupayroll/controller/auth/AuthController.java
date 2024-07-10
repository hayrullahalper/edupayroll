package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.dto.auth.*;
import com.incubator.edupayroll.service.auth.AuthService;
import com.incubator.edupayroll.service.token.TokenService;
import com.incubator.edupayroll.service.user.UserService;
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
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService, UserService userService) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.userService = userService;
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

        var token = generateUserToken(input.getEmail());
        authService.register(input.getEmail(), token);

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

    @PostMapping("/password/forgot")
    public ResponseEntity<Response<PasswordPayload, AuthErrorCode>> forgotPassword(
            @RequestBody ForgotPasswordInput input) {

        Validation.validate(input);

        var email = input.getEmail();
        var user = userService.getByEmail(email);

        if (user == null)
            return ResponseEntity.ok().body(Response.data(new PasswordPayload(false)).build());

        var token = generateUserToken(email);

        authService.forgotPassword(email, token);

        return ResponseEntity.ok().body(Response.data(new PasswordPayload(true)).build());
    }

    @PostMapping("/password/change")
    public ResponseEntity<Response<PasswordPayload, AuthErrorCode>> changePassword(
            @RequestBody ChangePasswordInput input) {

        Validation.validate(input);

        var context = tokenService.decode(input.getToken());
        var email = context.get("email").asString();

        authService.changePassword(email, input.getNewPassword());

        return ResponseEntity.ok().body(Response.data(new PasswordPayload(true)).build());
    }

    private String generateUserToken(String email) {
        return tokenService.encode(new HashMap<>(Map.of("email", email)));
    }

}
