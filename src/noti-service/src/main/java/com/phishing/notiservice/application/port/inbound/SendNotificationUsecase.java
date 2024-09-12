package com.phishing.notiservice.application.port.inbound;


public interface SendNotificationUsecase {
    void sendNotification(SendNotificationEvent sendNotificationEvent);
}
