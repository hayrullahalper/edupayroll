package com.incubator.edupayroll.util.validation;

public class Validation {
    public static <T> void validate(T object) {
        var vf = jakarta.validation.Validation.buildDefaultValidatorFactory();
        var validator = vf.getValidator();

        var violations = validator.validate(object);

        if (!violations.isEmpty()) {
            throw new InvalidConstraintsException(violations);
        }
    }
}
