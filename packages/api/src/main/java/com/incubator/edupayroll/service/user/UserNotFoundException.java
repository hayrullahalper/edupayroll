package com.incubator.edupayroll.service.user;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }

  public static UserNotFoundException byId(String id) {
    return new UserNotFoundException("User not found with id: " + id);
  }

  public static UserNotFoundException byEmail(String email) {
    return new UserNotFoundException("User not found with email: " + email);
  }
}
