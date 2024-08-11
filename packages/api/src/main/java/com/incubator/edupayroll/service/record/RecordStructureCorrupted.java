package com.incubator.edupayroll.service.record;

import java.util.UUID;

public class RecordStructureCorrupted extends RuntimeException {
  public RecordStructureCorrupted(String message) {
    super(message);
  }

  public static RecordStructureCorrupted byId(UUID id) {
    return new RecordStructureCorrupted("Record structure corrupted by id: " + id);
  }
}
