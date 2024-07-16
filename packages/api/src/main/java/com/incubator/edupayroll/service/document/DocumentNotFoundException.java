package com.incubator.edupayroll.service.document;

import java.util.UUID;

public class DocumentNotFoundException extends RuntimeException {

  DocumentNotFoundException(String message) {
    super(message);
  }

  public static DocumentNotFoundException byId(UUID uuid) {
    return new DocumentNotFoundException("Document not found with id: " + uuid);
  }
}
