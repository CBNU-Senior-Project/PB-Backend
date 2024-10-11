package com.phishing.userservice.domain.group.repository;

import com.phishing.userservice.domain.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // 그룹 이름으로 검색
    List<Group>findByName(String name);

    // 특정 사용자가 생성한 그룹들 조회


    List<Group> findByCreator_UserId(Long creatorId);


}
