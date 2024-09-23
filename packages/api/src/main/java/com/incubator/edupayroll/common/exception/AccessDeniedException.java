package com.incubator.edupayroll.common.exception;

import com.incubator.edupayroll.entity.user.UserEntity;

public class AccessDeniedException extends RuntimeException {
  public AccessDeniedException(String message) {
    super(message);
  }

  public static AccessDeniedException byUser(UserEntity user) {
    return new AccessDeniedException("Access denied for user: " + user.toString());
  }
}
