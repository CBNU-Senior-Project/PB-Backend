package com.phishing.detectedservice.adapter.kafka;

import com.phishing.common.event.events.DlqMessage;
import com.phishing.common.event.events.PhishingDetectedEvent;
import com.phishing.detectedservice.dto.event.PhishingDetectedSpringEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class DetectionKafkaProducer {
    private final KafkaTemplate<String, PhishingDetectedEvent> kafkaTemplate;
    private final KafkaTemplate<String, DlqMessage> dlqKafkaTemplate;
    private static final String TOPIC = "phishing-detected";


    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @org.springframework.retry.annotation.Backoff(delay = 1000)
    )
    @EventListener
    public void send(PhishingDetectedSpringEvent event){
        PhishingDetectedEvent message = PhishingDetectedEvent.builder()
                .detectionId(event.detectionId())
                .userId(event.userId())
                .text(event.text())
                .probability(event.probability())
                .isPhishing(event.isPhishing())
                .detectedAt(event.detectedAt())
                .build();
        kafkaTemplate.send(TOPIC, event.detectionId(), message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka 전송 실패 - ID: {}, error: {}", message.getDetectionId(), ex.getMessage());
                    } else {
                        log.info("Kafka 전송 성공 - ID: {}", message.getDetectionId());
                    }
                });
    }

    @Recover
    public void recover(Exception e, PhishingDetectedSpringEvent event) {
        PhishingDetectedEvent message = PhishingDetectedEvent.builder()
                .detectionId(event.detectionId())
                .userId(event.userId())
                .text(event.text())
                .probability(event.probability())
                .isPhishing(event.isPhishing())
                .detectedAt(event.detectedAt())
                .build();
        String detectionId = event.detectionId();

        log.error("Kafka 전송 재시도 모두 실패 - DLQ 전송 시작 (ID: {})", detectionId);

        DlqMessage dlq = DlqMessage.builder()
                .originalMessage(message)
                .sourceTopic(TOPIC)
                .failedAt(LocalDateTime.now())
                .errorMessage(e.getMessage())
                .retryCount(3)
                .build();

        dlqKafkaTemplate.send(TOPIC + "-dlq", detectionId, dlq);
    }
}
