package com.phishing.notiservice.adapter.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotiUserRepository extends JpaRepository<NotiUserEntity, Long> {
    List<NotiUserEntity> findByGroupIdAndIsDeletedFalse(Long groupId);
}
