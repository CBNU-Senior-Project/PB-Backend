package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.application.port.outbound.LoadNotificationPort;
import com.phishing.notiservice.application.port.outbound.SaveNotificationPort;
import com.phishing.notiservice.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements SaveNotificationPort, LoadNotificationPort {

    private final NotificationRepository notificationRepository;
    @Override
    public void saveNotification(Notification notification) {
        // Save notification to database
        NotificationEntity notificationEntity = NotificationMapper.toEntity(notification);
        notificationRepository.save(notificationEntity);
    }

    @Override
    public List<Notification> loadNotificationByUserId(Long userId) {
        // Load notification from database
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByUserInfo_UserIdAndIsDeletedFalse(userId);
        return notificationEntities.stream()
                .map(NotificationMapper::toDomain)
                .toList();
    }
}
