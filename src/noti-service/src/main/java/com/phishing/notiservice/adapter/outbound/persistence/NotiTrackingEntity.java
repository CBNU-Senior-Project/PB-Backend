package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.NotiStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@DynamicUpdate
@Entity
public class NotiTrackingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_tracking_id")
    private Long notiTrackingId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotiStatus status;

    public static NotiTrackingEntity create(Long userId, Long notificationId, NotiStatus status) {
        return NotiTrackingEntity.builder()
                .userId(userId)
                .notificationId(notificationId)
                .isRead(false)
                .sentAt(LocalDateTime.now())
                .readAt(null)
                .status(status)
                .build();
    }
}
