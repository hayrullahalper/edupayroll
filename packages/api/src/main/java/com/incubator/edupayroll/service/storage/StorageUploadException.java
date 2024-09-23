package com.incubator.edupayroll.service.storage;

public class StorageUploadException extends RuntimeException {

  public StorageUploadException(String message, Throwable cause) {
    super(message, cause);
  }

  public static StorageUploadException byException(Throwable cause) {
    return new StorageUploadException("Failed to upload file", cause);
  }
}
