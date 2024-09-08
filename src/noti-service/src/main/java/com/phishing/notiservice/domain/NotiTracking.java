package com.phishing.notiservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class NotiTracking {
    private Long notiTrackingId;

    private Long userId;

    private Long notificationId;

    private boolean isRead;

    private LocalDateTime sentAt;

    private LocalDateTime readAt;

    private NotiStatus status;

    public static NotiTracking create(Long userId, Long notificationId, NotiStatus status) {
        return NotiTracking.builder()
                .userId(userId)
                .notificationId(notificationId)
                .isRead(false)
                .sentAt(LocalDateTime.now())
                .readAt(null)
                .status(status)
                .build();
    }
}
