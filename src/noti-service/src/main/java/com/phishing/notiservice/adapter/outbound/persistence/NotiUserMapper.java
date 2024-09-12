package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.NotiUser;
import org.springframework.stereotype.Component;

@Component
public class NotiUserMapper {
    public static NotiUser toDomain(NotiUserEntity notiUserEntity) {
        return NotiUser.builder()
                .notiUserId(notiUserEntity.getNotiUserId())
                .userId(notiUserEntity.getUserId())
                .groupId(notiUserEntity.getGroupId())
                .deviceInfo(notiUserEntity.getDeviceInfo())
                .isActive(notiUserEntity.isActive())
                .build();
    }

    public static NotiUserEntity toEntity(NotiUser notiUser) {
        return NotiUserEntity.create(
                notiUser.getUserId(),
                notiUser.getGroupId(),
                notiUser.getDeviceInfo(),
                notiUser.isActive()
        );
    }
}
