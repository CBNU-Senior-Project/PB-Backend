package com.phishing.notiservice.adapter.inbound.eventbroker;

import com.phishing.common.event.events.PredictFinishedEvent;
import com.phishing.notiservice.application.port.inbound.NotificationUsecase;
import com.phishing.notiservice.application.service.NotificationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class KafkaEventConsumer {

    private final NotificationUsecase notificationUsecase;

    @KafkaListener(topics = "phishing-detection", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePredictFinishedEvent(PredictFinishedEvent predictFinishedEvent) {
        log.info("Consumed message: {}", predictFinishedEvent);
        notificationUsecase.sendNotification(predictFinishedEvent);
    }
}
