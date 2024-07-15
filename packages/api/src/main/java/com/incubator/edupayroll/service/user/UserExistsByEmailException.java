package com.incubator.edupayroll.service.user;

import com.incubator.edupayroll.entity.user.UserEntity;

public class UserExistsByEmailException extends RuntimeException {

  public UserExistsByEmailException(String message) {
    super(message);
  }

  public static UserExistsByEmailException byUser(UserEntity user) {
    return new UserExistsByEmailException("User exists by mail: " + user.getEmail());
  }
}
