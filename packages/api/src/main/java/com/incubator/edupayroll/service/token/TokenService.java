package com.incubator.edupayroll.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.incubator.edupayroll.entity.User;
import com.incubator.edupayroll.service.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
    private final int expiration = 7 * 24 * 60 * 60;

    private final UserDetailsService userDetailsService;

    private final Algorithm algorithm = Algorithm.HMAC256("baeldung");

    @Autowired
    public TokenService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public boolean verify(String token) {
        return JWT.require(algorithm).build().verify(token) != null;
    }

    public String encode(User user) {
        var expiresAt = Instant.now().plusSeconds(expiration);

        return JWT.create()
                .withClaim("email", user.getEmail())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public UserDetails decode(String token) {
        try {
            var userId = JWT.decode(token).getClaim("email").asString();
            return userDetailsService.loadUserByUsername(userId);
        } catch (TokenExpiredException e) {
            throw InvalidTokenException.byExpiredToken(token);
        } catch (UserNotFoundException e) {
            throw InvalidTokenException.byInvalidToken(token);
        } catch (Exception e) {
            throw InvalidTokenException.byInvalidToken(token, e);
        }
    }
}
