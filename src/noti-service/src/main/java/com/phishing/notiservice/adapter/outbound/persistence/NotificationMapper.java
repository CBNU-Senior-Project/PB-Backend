package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.NotiUserInfo;
import com.phishing.notiservice.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public static NotificationEntity toEntity(Notification notification) {
        return NotificationEntity.builder()
                .notificationId(notification.getNotificationId())
                .payload(notification.getPayload())
                .notiType(notification.getNotiType())
                .userInfo(NotiUserInfo.create(notification.getUserId(), notification.getTargetGroupId()))
                .build();
    }

    public static Notification toDomain(NotificationEntity notificationEntity) {
        return Notification.builder()
                .notificationId(notificationEntity.getNotificationId())
                .payload(notificationEntity.getPayload())
                .notiType(notificationEntity.getNotiType())
                .userId(notificationEntity.getUserInfo().getUserId())
                .targetGroupId(notificationEntity.getUserInfo().getTargetGroupId())
                .createdAt(notificationEntity.getCreatedAt())
                .updatedAt(notificationEntity.getUpdatedAt())
                .build();
    }
}
