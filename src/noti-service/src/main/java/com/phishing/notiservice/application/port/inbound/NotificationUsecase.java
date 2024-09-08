package com.phishing.notiservice.application.port.inbound;


import com.phishing.common.event.events.PredictFinishedEvent;

public interface NotificationUsecase {
    void sendNotification(PredictFinishedEvent predictFinishedEvent);
}
