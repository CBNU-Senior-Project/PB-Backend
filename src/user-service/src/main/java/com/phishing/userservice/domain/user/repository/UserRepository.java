package com.phishing.userservice.domain.user.repository;

import com.phishing.userservice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserCertification_Email(String email);
    Optional<User> findByUserIdAndIsDeletedIsFalse(Long id);
    Optional<User> findByUserCertification_EmailAndIsDeletedIsFalse(String email);

    // 핸드폰 번호로 사용자 찾기taegung
    Optional<User> findByUserInfo_Phnum(String phoneNumber);

}
