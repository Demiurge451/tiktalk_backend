package com.edu.tiktalk_backend.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {
    private final MinioClient minioClient;
    private final String bucketName = "my-bucket";

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadFile(MultipartFile file) {
        try {
            boolean find = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!find) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            InputStream inputStream = file.getInputStream();
            String objectName = file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFile(String objectName) {
        try {
            Map<String, String> reqParams = new HashMap<String, String>();
            reqParams.put("response-content-type", "application/json");
            String url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .expiry(2, TimeUnit.HOURS)
                                    .extraQueryParams(reqParams)
                                    .build());
            return url;

        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
        return null;
    }
}
