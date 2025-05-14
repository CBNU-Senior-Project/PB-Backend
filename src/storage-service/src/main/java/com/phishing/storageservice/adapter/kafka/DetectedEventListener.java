package com.phishing.storageservice.adapter.kafka;

import com.phishing.common.event.events.PhishingDetectedEvent;
import com.phishing.storageservice.dto.request.TrainDataSaveRequest;
import com.phishing.storageservice.service.TrainDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DetectedEventListener {

    private final TrainDataService trainDataService;

    @KafkaListener(
            topics = "phishing-detected",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "phishingDetectedKafkaListenerContainerFactory"
    )
    public void consumePredictFinishedEvent(PhishingDetectedEvent event){

        if (event.getDetectionId().equals("abc123")) {
            throw new IllegalArgumentException("💥 강제 예외 발생");
        }

        log.info("Consumed message: {}", event.getDetectionId());
        TrainDataSaveRequest request = TrainDataSaveRequest.builder()
                .detectionId(event.getDetectionId())
                .userId(event.getUserId())
                .text(event.getText())
                .probability(event.getProbability())
                .isPhishing(event.isPhishing())
                .detectedAt(event.getDetectedAt())
                .build();

        trainDataService.saveTrainData(request);
    }
}
