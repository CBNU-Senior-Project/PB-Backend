package com.phishing.notiservice.application.port.outbound;

import com.phishing.notiservice.domain.NotiUser;

import java.util.List;

public interface LoadNotiUserPort {
    NotiUser loadNotiUser(Long userId);
    List<NotiUser> loadNotiUserByGroupId(Long groupId);
}
