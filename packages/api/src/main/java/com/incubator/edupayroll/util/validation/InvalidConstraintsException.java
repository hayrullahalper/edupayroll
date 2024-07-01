package com.incubator.edupayroll.util.validation;

import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter
public class InvalidConstraintsException extends RuntimeException {
  private final List<String> violations;

  public <T> InvalidConstraintsException(Set<ConstraintViolation<T>> violations) {
    super();

    this.violations = violations.stream().map(ConstraintViolation::getMessage).toList();
  }
}
