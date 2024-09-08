package com.phishing.notiservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotiUser {
    private Long notiUserId;

    private Long userId;

    private Long groupId;

    private DeviceInfo deviceInfo;

    private boolean isActive;

    public static NotiUser create(Long userId, Long groupId, DeviceInfo deviceInfo) {
        return NotiUser.builder()
                .userId(userId)
                .groupId(groupId)
                .deviceInfo(deviceInfo)
                .isActive(true)
                .build();
    }
}
