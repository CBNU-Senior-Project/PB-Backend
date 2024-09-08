package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.NotiTracking;
import org.springframework.stereotype.Component;

@Component
public class NotiTrackingMapper {
    public static NotiTrackingEntity toEntity(NotiTracking notiTracking) {
        return NotiTrackingEntity.builder()
                .notiTrackingId(notiTracking.getNotiTrackingId())
                .notificationId(notiTracking.getNotificationId())
                .userId(notiTracking.getUserId())
                .readAt(notiTracking.getReadAt())
                .sentAt(notiTracking.getSentAt())
                .isRead(notiTracking.isRead())
                .status(notiTracking.getStatus())
                .build();
    }
}
