package com.phishing.notiservice.adapter.inbound.eventbroker;

import com.phishing.common.event.events.PhishingDetectedEvent;
import com.phishing.common.event.events.PredictFinishedEvent;
import com.phishing.notiservice.application.port.inbound.SendNotificationEvent;
import com.phishing.notiservice.application.port.inbound.SendNotificationUsecase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final SendNotificationUsecase sendNotificationUsecase;

    @KafkaListener(
            topics = "phishing-detected",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "phishingDetectedKafkaListenerContainerFactory"
    )
    public void consumePredictFinishedEvent(PhishingDetectedEvent event) {
        log.info("Consumed message: {}", event);
        SendNotificationEvent appEvent = SendNotificationEvent.builder()
                .userId(event.getUserId())
                .isPhishing(event.getProbability() > 80)
                .probability(Double.toString(event.getProbability()))
                .build();
        sendNotificationUsecase.sendNotification(appEvent);
    }
}
