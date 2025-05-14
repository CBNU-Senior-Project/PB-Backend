package com.phishing.notiservice.application.port.inbound;

import lombok.Builder;

@Builder
public record SendNotificationEvent (
        Long userId,
        Boolean isPhishing,
        String probability
){
}
