package com.phishing.notiservice.application.port.inbound;

import com.phishing.notiservice.domain.NotiType;
import com.phishing.notiservice.domain.Notification;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record ViewNotiListResponse(
        String title,
        String message,
        NotiType notiType,
        Long targetGroupId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ViewNotiListResponse from(Notification notification) {
        return ViewNotiListResponse.builder()
                .title(notification.getPayload().getTitle())
                .message(notification.getPayload().getMessage())
                .notiType(notification.getNotiType())
                .targetGroupId(notification.getTargetGroupId())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}
