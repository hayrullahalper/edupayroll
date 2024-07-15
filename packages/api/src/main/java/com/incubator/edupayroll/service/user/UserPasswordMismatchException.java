package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.user.UserEntity;

public class UserPasswordMismatchException extends RuntimeException {
  public UserPasswordMismatchException(String message) {
    super(message);
  }

  public static UserPasswordMismatchException byUser(UserEntity user) {
    return new UserPasswordMismatchException("Password mismatch for user: " + user.getEmail());
  }
}
