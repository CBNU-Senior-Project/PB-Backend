package com.phishing.notiservice.application.port.inbound;

public record SendNotificationEvent (
        Long userId,
        boolean isPhishing,
        String probability
){
}
