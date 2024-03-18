package com.incubator.edupayroll.service.school;

import java.util.UUID;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException(UUID id) {
        super("School not found with id: " + id);
    }

}
