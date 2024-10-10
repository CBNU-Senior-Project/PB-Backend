package com.phishing.userservice.domain.phish.repository;

import com.phishing.userservice.domain.phish.domain.Phishing;
import com.phishing.userservice.domain.phish.domain.PhishingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhishingRepository extends JpaRepository<Phishing, Long> {
    List<Phishing> findByPhishingType(PhishingType type);

    List<Phishing> findByPhishingTypeAndValueContaining(PhishingType phishingType, String value);
}
