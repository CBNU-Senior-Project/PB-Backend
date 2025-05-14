package com.phishing.storageservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TrainData {
    @Id
    private String detectionId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "probability", nullable = false)
    private double probability;
    @Column(name = "is_phishing", nullable = false)
    private boolean isPhishing;
    @Column(name = "detected_at", nullable = false)
    private LocalDateTime detectedAt;

    public static TrainData of(String detectionId, Long userId, String text, double probability, boolean isPhishing, LocalDateTime detectedAt) {
        return TrainData.builder()
                .detectionId(detectionId)
                .userId(userId)
                .text(text)
                .probability(probability)
                .isPhishing(isPhishing)
                .detectedAt(detectedAt)
                .build();
    }
}
