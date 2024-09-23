package com.incubator.edupayroll.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
  @Value("${aws.s3.bucket}")
  private String bucketName;

  private final AmazonS3 s3;

  @Autowired
  public StorageService(AmazonS3 s3) {
    this.s3 = s3;
  }

  public void uploadFile(String path, InputStream file) {
    try {
      s3.putObject(bucketName, path, file, null);
    } catch (Exception e) {
      throw StorageUploadException.byException(e);
    }
  }

  public InputStream downloadFile(String path) {
    try {
      return s3.getObject(bucketName, path).getObjectContent();
    } catch (AmazonS3Exception e) {
      throw StorageDownloadException.byException(e);
    }
  }

  public StorageFileMetadata getFileMetadata(String path) {
    try {
      var data = s3.getObjectMetadata(bucketName, path);
      var metadata = new StorageFileMetadata();

      metadata.url = s3.getUrl(bucketName, path).toString();
      metadata.path = path;
      metadata.name = path.substring(path.lastIndexOf('/') + 1);
      metadata.contentLength = data.getContentLength();
      metadata.contentType = data.getContentType();
      metadata.contentEncoding = data.getContentEncoding();

      return metadata;
    } catch (AmazonS3Exception e) {
      throw StorageMetadataException.byException(e);
    }
  }

  public void deleteFile(String path) {
    try {
      s3.deleteObject(bucketName, path);
    } catch (AmazonS3Exception e) {
      throw StorageDeleteException.byException(e);
    }
  }
}
