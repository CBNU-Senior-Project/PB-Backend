package com.phishing.storageservice.service;

import com.phishing.storageservice.dto.request.TrainDataSaveRequest;
import com.phishing.storageservice.entity.TrainData;
import com.phishing.storageservice.repository.TrainDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainDataService {

    private final TrainDataRepository trainDataRepository;

    public void saveTrainData(TrainDataSaveRequest request) {
        TrainData trainData = TrainData.builder()
                .detectionId(request.detectionId())
                .userId(request.userId())
                .text(request.text())
                .probability(request.probability())
                .isPhishing(request.isPhishing())
                .detectedAt(request.detectedAt())
                .build();

        trainDataRepository.save(trainData);
    }
}
