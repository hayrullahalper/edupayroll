package com.incubator.edupayroll.mapper.user;

import com.incubator.edupayroll.dto.user.User;
import com.incubator.edupayroll.entity.user.UserEntity;

public class UserMapper {
  public static User toDTO(UserEntity user) {
    return new User(user.getId(), user.getName(), user.getEmail());
  }
}
