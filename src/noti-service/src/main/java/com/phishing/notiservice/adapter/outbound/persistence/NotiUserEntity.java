package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.BaseEntity;
import com.phishing.notiservice.domain.DeviceInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
public class NotiUserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_user_id")
    private Long notiUserId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    @Embedded
    private DeviceInfo deviceInfo;

    @Column(name = "is_active")
    private boolean isActive;

}
