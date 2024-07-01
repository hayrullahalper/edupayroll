package com.incubator.edupayroll.service.teacher;

import com.incubator.edupayroll.entity.user.UserEntity;
import java.util.UUID;

public class TeacherNotFoundException extends RuntimeException {
  public TeacherNotFoundException(String message) {
    super(message);
  }

  public static TeacherNotFoundException byId(UUID id) {
    return new TeacherNotFoundException("Teacher not found by id: " + id);
  }

  public static TeacherNotFoundException byUser(UserEntity user) {
    return new TeacherNotFoundException("Teacher not found by user: " + user);
  }
}
