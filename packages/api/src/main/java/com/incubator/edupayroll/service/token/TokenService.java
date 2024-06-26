package com.incubator.edupayroll.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.incubator.edupayroll.service.user.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final int expiration = 7 * 24 * 60 * 60;

    private final Algorithm algorithm = Algorithm.HMAC256("baeldung");

    public boolean verify(String token) {
        return JWT.require(algorithm).build().verify(token) != null;
    }

    public String encode(Map<String, String> claims) {
        var expiresAt = Instant.now().plusSeconds(expiration);

        var jwt = JWT.create();

        claims.forEach(jwt::withClaim);

        return jwt.withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public Map<String, String> decode(String token) {
        try {
            return JWT.decode(token)
                    .getClaims()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().asString()));

        } catch (TokenExpiredException e) {
            throw InvalidTokenException.byExpiredToken(token);
        } catch (UserNotFoundException e) {
            throw InvalidTokenException.byInvalidToken(token);
        } catch (Exception e) {
            throw InvalidTokenException.byInvalidToken(token, e);
        }
    }

}
