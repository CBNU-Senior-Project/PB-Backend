package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.application.port.outbound.SaveNotiTrackingPort;
import com.phishing.notiservice.domain.NotiTracking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotiTrackingPersistenceAdapter implements SaveNotiTrackingPort {

        private final NotiTrackingRepository notiTrackingRepository;
        @Override
        public void saveNotiTracking(List<NotiTracking> notiTrackings) {
            // Save noti tracking to database
            List<NotiTrackingEntity> notiTrackingEntities = notiTrackings.stream()
                    .map(NotiTrackingMapper::toEntity)
                    .collect(Collectors.toList());
            notiTrackingRepository.saveAll(notiTrackingEntities);
        }
}
