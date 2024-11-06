package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public static NotificationEntity toEntity(Notification notification) {
        return NotificationEntity.builder()
                .notificationId(notification.getNotificationId())
                .payload(notification.getPayload())
                .notiType(notification.getNotiType())
                .targetGroupId(notification.getTargetGroupId())
                .build();
    }
}
