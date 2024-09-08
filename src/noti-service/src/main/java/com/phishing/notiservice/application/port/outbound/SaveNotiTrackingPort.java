package com.phishing.notiservice.application.port.outbound;

import com.phishing.notiservice.domain.NotiTracking;

import java.util.List;

public interface SaveNotiTrackingPort {
    void saveNotiTracking(List<NotiTracking> notiTrackingEntities);
}
