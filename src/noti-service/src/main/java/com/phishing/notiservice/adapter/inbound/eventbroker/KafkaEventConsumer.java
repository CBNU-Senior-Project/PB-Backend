package com.phishing.notiservice.adapter.inbound.eventbroker;

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

    @KafkaListener(topics = "phishing-detection", groupId = "${spring.kafka.consumer.group-id}",
    containerFactory = "kafkaListenerContainerFactory")
    public void consumePredictFinishedEvent(PredictFinishedEvent predictFinishedEvent) {
        log.info("Consumed message: {}", predictFinishedEvent);
        SendNotificationEvent event = new SendNotificationEvent(
                predictFinishedEvent.getUserId(),
                predictFinishedEvent.getIsPhishing().equals("true"),
                predictFinishedEvent.getProbability()
        );
        sendNotificationUsecase.sendNotification(event);
    }
}
