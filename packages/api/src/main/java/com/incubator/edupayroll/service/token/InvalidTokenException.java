package com.incubator.edupayroll.service.token;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public static InvalidTokenException byInvalidToken(String token) {
        return new InvalidTokenException("Invalid token: " + token);
    }

    public static InvalidTokenException byExpiredToken(String token) {
        return new InvalidTokenException("Token expired: " + token);
    }

    public static InvalidTokenException byInvalidToken(String token, Exception e) {
        return new InvalidTokenException("Invalid token: " + token + " " + e.getMessage());
    }
}
