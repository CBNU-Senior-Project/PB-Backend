package com.phishing.notiservice.adapter.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiTrackingRepository extends JpaRepository<NotiTrackingEntity, Long> {
}
