package com.incubator.edupayroll.service.export;

import java.util.UUID;

public class ExportNotFoundException extends RuntimeException {
  public ExportNotFoundException(String message) {
    super(message);
  }

  public static ExportNotFoundException byId(UUID id) {
    return new ExportNotFoundException("Export not found by id: " + id);
  }
}
