package com.phishing.notiservice.application.port.outbound;

import com.phishing.notiservice.domain.NotiUser;

public interface SaveNotiUserPort {
    void saveNotiUser(NotiUser notiUser);
}
