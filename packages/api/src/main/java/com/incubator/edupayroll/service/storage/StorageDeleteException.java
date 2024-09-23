package com.incubator.edupayroll.service.storage;

public class StorageDeleteException extends RuntimeException {

  public StorageDeleteException(String message, Throwable cause) {
    super(message, cause);
  }

  public static StorageDeleteException byException(Throwable cause) {
    return new StorageDeleteException("Failed to delete file", cause);
  }
}
