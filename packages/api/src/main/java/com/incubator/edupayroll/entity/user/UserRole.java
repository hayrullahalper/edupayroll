package com.incubator.edupayroll.entity.user;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }
}
