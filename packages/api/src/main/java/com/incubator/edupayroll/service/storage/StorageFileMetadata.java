package com.incubator.edupayroll.service.storage;

import lombok.Getter;

@Getter
public class StorageFileMetadata {
  public String url;
  public String path;
  public String name;
  public long contentLength;
  public String contentType;
  public String contentEncoding;
}
