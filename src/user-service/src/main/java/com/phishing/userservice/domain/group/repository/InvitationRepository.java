package com.phishing.userservice.domain.group.repository;

import com.phishing.userservice.domain.group.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findByReceiver_UserId(Long userId);
    List<Invitation> findByStatus(String status);
}
