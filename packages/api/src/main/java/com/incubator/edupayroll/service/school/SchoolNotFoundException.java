package com.incubator.edupayroll.service.school;

import com.incubator.edupayroll.entity.user.UserEntity;

import java.util.UUID;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException(String message) {
        super(message);
    }

    public static SchoolNotFoundException byId(UUID id) {
        return new SchoolNotFoundException("School not found by id: " + id.toString());
    }

    public static SchoolNotFoundException byUser(UserEntity user) {
        return new SchoolNotFoundException("School not found by user: " + user);
    }
}
