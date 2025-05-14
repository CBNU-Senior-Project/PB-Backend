package com.phishing.detectedservice.dto.event;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PhishingDetectedSpringEvent(
        String detectionId,
        Long userId,
        String text,
        double probability,
        boolean isPhishing,
        LocalDateTime detectedAt
) {}
