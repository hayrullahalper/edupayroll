package com.incubator.edupayroll.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import java.time.Instant;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  private final int expiration = 7 * 24 * 60 * 60;

  private final Algorithm algorithm = Algorithm.HMAC256("baeldung");

  public boolean verify(String token) {
    try {
      return JWT.require(algorithm).build().verify(token) != null;
    } catch (JWTDecodeException e) {
      return false;
    }
  }

  public String encode(Map<String, Object> claims) {
    return encode(claims, expiration);
  }

  public String encode(Map<String, Object> claims, int expiration) {
    var expiresAt = Instant.now().plusSeconds(expiration);

    var jb = JWT.create();

    claims.forEach(
        (key, value) -> {
          if (value instanceof String) {
            jb.withClaim(key, (String) value);
            return;
          }

          if (value instanceof Integer) {
            jb.withClaim(key, (Integer) value);
            return;
          }

          if (value instanceof Boolean) {
            jb.withClaim(key, (Boolean) value);
            return;
          }

          throw new IllegalArgumentException("Unsupported claim type");
        });

    return jb.withExpiresAt(java.util.Date.from(expiresAt)).sign(algorithm);
  }

  public Map<String, Claim> decode(String token) {
    try {
      return JWT.decode(token).getClaims();
    } catch (TokenExpiredException e) {
      throw InvalidTokenException.byExpiredToken(token);
    } catch (Exception e) {
      throw InvalidTokenException.byInvalidToken(token, e);
    }
  }
}
