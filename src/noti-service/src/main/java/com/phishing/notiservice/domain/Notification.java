package com.phishing.notiservice.domain;

import com.phishing.notiservice.adapter.outbound.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {
    private Long notificationId;

    private NotiPayload payload;

    private NotiType notiType;

    private Long targetGroupId;

    public static Notification create(NotiPayload payload, NotiType notiType, Long targetGroupId) {
        return Notification.builder()
                .payload(payload)
                .notiType(notiType)
                .targetGroupId(targetGroupId)
                .build();
    }
}
