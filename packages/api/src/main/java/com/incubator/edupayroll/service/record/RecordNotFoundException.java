package com.incubator.edupayroll.service.record;

import java.util.UUID;

public class RecordNotFoundException extends RuntimeException {
  public RecordNotFoundException(String message) {
    super(message);
  }

  public static RecordNotFoundException byId(UUID id) {
    return new RecordNotFoundException("Record not found by id: " + id);
  }
}
