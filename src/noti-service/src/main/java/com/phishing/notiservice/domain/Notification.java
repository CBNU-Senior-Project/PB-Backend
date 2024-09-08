package com.phishing.notiservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Notification extends BaseEntity{
    private Long notificationId;

    private NotiPayload payload;

    private NotiType notiType;

    private Long targetGroupId;

    @Builder
    public static Notification create(NotiPayload payload, NotiType notiType, Long targetGroupId) {
        return Notification.builder()
                .payload(payload)
                .notiType(notiType)
                .targetGroupId(targetGroupId)
                .build();
    }
}
