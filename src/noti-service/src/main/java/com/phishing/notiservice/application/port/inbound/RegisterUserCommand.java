package com.phishing.notiservice.application.port.inbound;

import com.phishing.notiservice.domain.DeviceInfo;

public record RegisterUserCommand (
        Long userId,
        Long groupId,
        DeviceInfo deviceInfo,
        boolean isNotiEnabled
){
}
