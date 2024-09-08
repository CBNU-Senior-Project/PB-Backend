package com.phishing.notiservice.application.port.outbound;

import com.phishing.notiservice.domain.Notification;

public interface SaveNotificationPort {
    void saveNotification(Notification notification);
}
