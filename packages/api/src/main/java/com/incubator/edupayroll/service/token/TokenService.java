package com.incubator.edupayroll.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.incubator.edupayroll.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  private final Algorithm algorithm;
  private final TokenRepository tokenRepository;


  @Autowired
  public TokenService(Environment env, TokenRepository tokenRepository) {
    this.algorithm = Algorithm.HMAC256(env.getProperty("app.jwt-secret"));
    this.tokenRepository = tokenRepository;
  }

  public boolean verify(String token) {
    try {
      return JWT.require(algorithm).build().verify(token) != null;
    } catch (JWTDecodeException e) {
      return false;
    }
  }

  public String encodeUserToken(UUID userId, String email) {
    var jb = generateTokenBuilder();

    jb.withClaim("userId", userId.toString());
    jb.withClaim("email", email);

    return jb.sign(algorithm);
  }

  public UserTokenPayload decodeUserToken(String token) {
    var claims = JWT.decode(token).getClaims();

    var email = claims.get("email").asString();
    var userId = UUID.fromString(claims.get("userId").asString());

    return new UserTokenPayload(userId, email);
  }

  private Builder generateTokenBuilder() {
    var jb = JWT.create();

    int expiration = 7 * 24 * 60 * 60; // 7 days
    var expiresAt = Instant.now().plusSeconds(expiration);

    jb.withExpiresAt(Date.from(expiresAt));
    return jb;
  }
}
