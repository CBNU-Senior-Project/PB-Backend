package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.application.port.outbound.SaveNotificationPort;
import com.phishing.notiservice.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements SaveNotificationPort {

    private final NotificationRepository notificationRepository;
    @Override
    public void saveNotification(Notification notification) {
        // Save notification to database
        NotificationEntity notificationEntity = NotificationMapper.toEntity(notification);
        notificationRepository.save(notificationEntity);
    }
}
