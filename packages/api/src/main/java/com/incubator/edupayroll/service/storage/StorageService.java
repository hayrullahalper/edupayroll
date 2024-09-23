package com.incubator.edupayroll.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.InputStream;
import java.util.Date;
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

  public void uploadFile(String path, InputStream stream) {
    try {
      var metadata = new ObjectMetadata();
      metadata.setContentLength(stream.available());

      s3.putObject(bucketName, path, stream, metadata);
    } catch (Exception e) {
      throw StorageUploadException.byException(e);
    }
  }

  public String createSignedUrl(String path) {
    var expiration = new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000);
    return s3.generatePresignedUrl(bucketName, path, expiration).toString();
  }

  public void deleteFile(String path) {
    try {
      s3.deleteObject(bucketName, path);
    } catch (AmazonS3Exception e) {
      throw StorageDeleteException.byException(e);
    }
  }
}
