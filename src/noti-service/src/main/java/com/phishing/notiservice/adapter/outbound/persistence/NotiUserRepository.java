package com.phishing.notiservice.adapter.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotiUserRepository extends JpaRepository<NotiUserEntity, Long> {
    Optional<NotiUserEntity> findByUserIdAndIsDeletedFalse(Long userId);
    List<NotiUserEntity> findByGroupIdAndIsDeletedFalse(Long groupId);
}
