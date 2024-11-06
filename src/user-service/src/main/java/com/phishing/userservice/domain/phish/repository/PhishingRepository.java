package com.phishing.userservice.domain.phish.repository;

import com.phishing.userservice.domain.phish.domain.Phishing;
import com.phishing.userservice.domain.phish.domain.PhishingType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhishingRepository extends JpaRepository<Phishing, Long> {
    // 기존 메서드들
    List<Phishing> findByPhishingType(PhishingType type);
    List<Phishing> findByPhishingTypeAndValueContaining(PhishingType phishingType, String value);
    List<Phishing> findByPhishingTypeAndValueStartingWith(PhishingType phishingType, String value);

    // 정확히 일치하는 데이터를 찾는 메서드 추가
    List<Phishing> findByPhishingTypeAndValue(PhishingType phishingType, String value);

    @Query("SELECT p FROM Phishing p WHERE p.phishingType = :phishingType ORDER BY p.createdAt DESC")
    List<Phishing> findTop2ByPhishingTypeOrderByCreatedDateDesc(@Param("phishingType") PhishingType phishingType, Pageable pageable);
}

