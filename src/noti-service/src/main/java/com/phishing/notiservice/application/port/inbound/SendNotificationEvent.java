package com.phishing.notiservice.application.port.inbound;

public record SendNotificationEvent (
        Long userId,
        Boolean isPhishing,
        String probability
){
}
