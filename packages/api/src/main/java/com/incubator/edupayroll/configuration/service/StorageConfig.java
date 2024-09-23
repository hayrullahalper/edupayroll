package com.incubator.edupayroll.configuration.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
  @Value("${aws.s3.region}")
  private String region;

  @Value("${aws.access.key}")
  private String accessKey;

  @Value("${aws.access.secret}")
  private String secretKey;

  @Bean
  public AmazonS3 amazonS3Client() {
    var credentials = new BasicAWSCredentials(accessKey, secretKey);

    return AmazonS3ClientBuilder.standard()
            .withRegion(getRegion(region))
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
  }

  private Regions getRegion(String region) {
    var regions = Regions.values();

    for (var r : regions) {
      if (r.getName().equals(region)) {
        return r;
      }
    }

    return Regions.US_EAST_1;
  }
}
