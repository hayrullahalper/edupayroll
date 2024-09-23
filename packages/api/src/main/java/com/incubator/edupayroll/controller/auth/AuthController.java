package com.incubator.edupayroll.controller.auth;

import com.incubator.edupayroll.dto.auth.*;
import com.incubator.edupayroll.service.auth.AuthService;
import com.incubator.edupayroll.service.token.TokenService;
import com.incubator.edupayroll.common.response.Response;
import java.util.HashMap;
import java.util.Map;
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

  private final int SHORT_EXPIRATION_TIME = 60 * 60;

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
    var token = generateVerifiedToken(user.getId().toString());

    return ResponseEntity.ok().body(Response.data(new TokenPayload(token)).build());
  }

  @PostMapping("/register")
  public ResponseEntity<Response<RegisterPayload, AuthErrorCode>> register(
      @RequestBody RegisterInput input) {
    Validation.validate(input);

    var token = generateUnverifiedToken(input.getEmail());
    authService.register(input.getEmail(), token);

    return ResponseEntity.ok().body(Response.data(new RegisterPayload(true)).build());
  }

  @PostMapping("/register/complete")
  public ResponseEntity<Response<TokenPayload, AuthErrorCode>> registerComplete(
      @RequestBody RegisterCompleteInput input) {
    Validation.validate(input);

    var context = tokenService.decode(input.getToken());
    var email = context.get("email").asString();

    var user =
        authService.completeRegister(
            input.getFirstName(),
            input.getLastName(),
            email,
            input.getTitle(),
            input.getPassword(),
            input.getSchoolName(),
            input.getPrincipalName());

    var token = generateVerifiedToken(user.getEmail());

    return ResponseEntity.ok().body(Response.data(new TokenPayload(token)).build());
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Response<ResetPasswordPayload, AuthErrorCode>> resetPassword(
      @RequestBody ResetPasswordInput input) {
    Validation.validate(input);

    var email = input.getEmail();
    var token = generateUnverifiedToken(email);

    authService.requestResetPassword(email, token);

    return ResponseEntity.ok().body(Response.data(new ResetPasswordPayload(true)).build());
  }

  @PostMapping("/reset-password/complete")
  public ResponseEntity<Response<ResetPasswordPayload, AuthErrorCode>> resetPasswordComplete(
      @RequestBody ResetPasswordCompleteInput input) {
    Validation.validate(input);

    var ctx = tokenService.decode(input.getToken());
    var email = ctx.get("email").asString();

    authService.resetPassword(email, input.getPassword());

    return ResponseEntity.ok().body(Response.data(new ResetPasswordPayload(true)).build());
  }

  private String generateVerifiedToken(String email) {
    return tokenService.encode(new HashMap<>(Map.of("userId", email, "verified", true)));
  }

  private String generateUnverifiedToken(String email) {
    return tokenService.encode(
        new HashMap<>(Map.of("email", email, "verified", false)), SHORT_EXPIRATION_TIME);
  }
}
