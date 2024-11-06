package com.phishing.notiservice.application.port.outbound;

import com.phishing.notiservice.domain.Notification;

import java.util.List;

public interface LoadNotificationPort {
    List<Notification> loadNotificationByUserId(Long userId);
}
