package com.incubator.edupayroll.service.user;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

    public static UserAlreadyRegisteredException byEmail(String email) {
        return new UserAlreadyRegisteredException("User already registered with email: " + email);
    }
}
