package com.incubator.edupayroll.service.storage;

public class StorageMetadataException extends RuntimeException {

  public StorageMetadataException(String message, Throwable cause) {
    super(message, cause);
  }

  public static StorageMetadataException byException(Throwable cause) {
    return new StorageMetadataException("Failed to get file metadata", cause);
  }
}
