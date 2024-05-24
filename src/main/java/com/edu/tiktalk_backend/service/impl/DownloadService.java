package com.edu.tiktalk_backend.service.impl;

import com.edu.tiktalk_backend.service.enums.BucketEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DownloadService {
    private final MinioClient minioClient;

    @Value("${minio.host}")
    private String downloadHost;

    @SneakyThrows
    public String upload(MultipartFile file, BucketEnum bucket) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new RuntimeException("Image must have name.");
        }
        createBucket(bucket);
        String fileName = generateFileName(file);
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(bucket.getValue())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return downloadHost + "/" + bucket.getValue() + "/" + fileName;
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + file.getOriginalFilename()
                .substring(file.getOriginalFilename()
                        .lastIndexOf("."));
    }

    @SneakyThrows
    private void createBucket(BucketEnum bucket)  {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket.getValue())
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket.getValue())
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
