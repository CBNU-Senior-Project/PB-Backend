package com.phishing.userservice.domain.group.service;


import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String generateSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();

        Map<String, String> extensionHeaders = new HashMap<>();
        String contentType = getContentType(objectName);

        extensionHeaders.put("Content-Type", contentType);

        URL signedUrl = storage.signUrl(blobInfo,
                15,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                Storage.SignUrlOption.withExtHeaders(extensionHeaders),
                Storage.SignUrlOption.withV4Signature());

        return signedUrl.toString();
    }

    public String downloadImage(String objectName) {
        Blob blob = storage.get(bucketName, objectName);
        if (blob == null) {
            throw new NoSuchElementException("Image not found for the given object name: " + objectName);
        }
        return blob.getMediaLink();
    }



    private static String getContentType(String objectName) {
        String contentType;
        int index = objectName.lastIndexOf(".");
        if(index != -1) {
            String extension = objectName.substring(index + 1).toLowerCase();
            contentType = switch (extension) {
                case "jpg", "jpeg" -> "image/jpeg";
                case "png" -> "image/png";
                case "gif" -> "image/gif";
                case "bmp" -> "image/bmp";
                case "webp" -> "image/webp";
                default -> "application/octet-stream";
            };
        } else {
            contentType = "application/octet-stream";
        }
        return contentType;
    }



}
