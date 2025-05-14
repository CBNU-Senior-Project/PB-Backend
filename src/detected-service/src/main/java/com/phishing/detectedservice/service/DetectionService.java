package com.phishing.detectedservice.service;

import com.phishing.detectedservice.dto.event.PhishingDetectedSpringEvent;
import com.phishing.detectedservice.inference.model.InferenceModel;
import com.phishing.detectedservice.inference.model.InferenceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DetectionService {
    private final InferenceModel inferenceModel;
    private final ApplicationEventPublisher eventPublisher;

    public InferenceResult detect(String input) {
        String detectionId = UUID.randomUUID().toString();
        InferenceResult result = inferenceModel.predict(input);

        PhishingDetectedSpringEvent event = PhishingDetectedSpringEvent.builder()
                .detectionId(detectionId)
                .text(input)
                .probability(result.probability())
                .isPhishing(result.isPhishing())
                .detectedAt(LocalDateTime.now())
                .build();

        eventPublisher.publishEvent(event);
        return result;
    }
}
