package com.incubator.edupayroll.service;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import com.incubator.edupayroll.service.token.InvalidTokenException;
import com.incubator.edupayroll.service.token.TokenService;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenServiceTest {
  @Autowired private Faker faker;

  @Autowired private TokenService tokenService;

  @Test
  @DisplayName("should verify token correctly")
  public void testVerifyToken() {
    var token = tokenService.encode(Map.of());

    assertTrue(tokenService.verify(token));
    assertFalse(tokenService.verify("invalid-token"));
  }

  @Test
  @DisplayName("should encode and decode token correctly with claims")
  public void testTokenServiceWithClaims() {
    Map<String, Object> claims =
        Map.of(
            "id", faker.idNumber().valid(),
            "email", faker.internet().emailAddress());

    var token = tokenService.encode(claims);

    assertNotNull(token);

    var decoded = tokenService.decode(token);

    assertEquals(claims.get("id"), decoded.get("id").asString());
    assertEquals(claims.get("email"), decoded.get("email").asString());
  }

  @Test
  @DisplayName("should encode and decode token correctly without claims")
  public void testTokenServiceWithoutClaims() {
    var token = tokenService.encode(Map.of());

    assertNotNull(token);

    var decoded = tokenService.decode(token);

    // expiresAt claim is added by default when no claims are provided
    assertEquals(1, decoded.size());
    assertNotNull(decoded.get("exp").asDate());
  }

  @Test
  @DisplayName("should throw exception when token is invalid")
  public void testInvalidToken() {
    assertThrows(InvalidTokenException.class, () -> tokenService.decode("invalid-token"));
  }
}
