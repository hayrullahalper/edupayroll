package com.incubator.edupayroll.service.storage;

public class StorageDownloadException extends RuntimeException {

  public StorageDownloadException(String message, Throwable cause) {
    super(message, cause);
  }

  public static StorageDownloadException byException(Throwable cause) {
    return new StorageDownloadException("Failed to download file", cause);
  }
}
