package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.BaseEntity;
import com.phishing.notiservice.domain.NotiPayload;
import com.phishing.notiservice.domain.NotiType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@DynamicUpdate
@Entity
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Embedded
    private NotiPayload payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "noti_type")
    private NotiType notiType;

    @Column(name = "target_group_id")
    private Long targetGroupId;

    public static NotificationEntity create(NotiPayload payload, NotiType notiType, Long targetGroupId) {
        return NotificationEntity.builder()
                .payload(payload)
                .notiType(notiType)
                .targetGroupId(targetGroupId)
                .build();
    }

}
