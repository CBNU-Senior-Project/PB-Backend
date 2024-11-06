package com.phishing.userservice.domain.group.repository;

import com.phishing.userservice.domain.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group>findByName(String name);

    List<Group> findByCreator_UserId(Long creatorId);


}
