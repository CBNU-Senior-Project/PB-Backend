package com.phishing.notiservice.domain;

import com.phishing.notiservice.adapter.outbound.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Notification {
    private Long notificationId;

    private NotiPayload payload;

    private NotiType notiType;

    private Long userId;

    private Long targetGroupId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private boolean isDeleted;

    public static Notification create(NotiPayload payload, NotiType notiType, Long userId, Long targetGroupId) {
        return Notification.builder()
                .payload(payload)
                .notiType(notiType)
                .userId(userId)
                .targetGroupId(targetGroupId)
                .build();
    }
}
