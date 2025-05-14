package com.phishing.storageservice.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TrainDataSaveRequest(
        String detectionId,
        Long userId,
        String text,
        double probability,
        boolean isPhishing,
        LocalDateTime detectedAt
) {
}
