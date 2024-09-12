package com.phishing.notiservice.adapter.inbound.web;

import com.phishing.notiservice.domain.DeviceInfo;

public record RegisterUserRequest(
        Long userId,
        Long groupId,
        DeviceInfo deviceInfo,
        boolean isNotiEnabled
) {
}
