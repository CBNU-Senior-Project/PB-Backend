package com.phishing.notiservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class NotiUserInfo {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "target_group_id")
    private Long targetGroupId;

    public static NotiUserInfo create(Long userId, Long targetGroupId) {
        return NotiUserInfo.builder()
                .userId(userId)
                .targetGroupId(targetGroupId)
                .build();
    }
}
