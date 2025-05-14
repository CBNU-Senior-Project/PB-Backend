package com.phishing.storageservice.repository;

import com.phishing.storageservice.entity.TrainData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainDataRepository extends JpaRepository<TrainData, Long> {
    // Custom query methods can be defined here if needed
}
