package com.phishing.notiservice.adapter.inbound.eventbroker;

import com.phishing.common.config.KafkaConsumerConfig;
import com.phishing.common.config.KafkaProducerConfig;
import com.phishing.common.event.KafkaEvent;
import com.phishing.common.event.events.PredictFinishedEvent;
import com.phishing.notiservice.application.port.inbound.SendNotificationEvent;
import com.phishing.notiservice.application.port.inbound.SendNotificationUsecase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Import({
        KafkaConsumerConfig.class,
        KafkaProducerConfig.class
})
@EmbeddedKafka(partitions = 1, topics = { "phishing-detection" }, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"}, ports = { 9092 })
@TestPropertySource(properties = {
        "spring.kafka.consumer.group-id=noti-service",
        "spring.kafka.bootstrap-servers=localhost:9092"
})
@ContextConfiguration(classes = { KafkaProducerConfig.class, KafkaConsumerConfig.class, KafkaEventConsumer.class })
@EnableKafka
class KafkaEventConsumerTest {

    @Autowired
    private KafkaTemplate<String, KafkaEvent> kafkaTemplate;

    @MockBean
    private SendNotificationUsecase sendNotificationUsecase;

    @InjectMocks
    private KafkaEventConsumer kafkaEventConsumer;

    @Test
    public void testConsumePredictFinishedEvent() throws InterruptedException {
        //Given
        PredictFinishedEvent predictFinishedEvent = new PredictFinishedEvent();
        predictFinishedEvent.setUserId(123L);
        predictFinishedEvent.setPhishing(true);
        predictFinishedEvent.setProbability("0.95");
        //When
        kafkaTemplate.send("phishing-detection", predictFinishedEvent);

        // 메시지가 소비되도록 기다림
        Thread.sleep(5000);

        //Then
        SendNotificationEvent sendNotificationEvent = new SendNotificationEvent(
                predictFinishedEvent.getUserId(),
                predictFinishedEvent.isPhishing(),
                predictFinishedEvent.getProbability()
        );
        verify(sendNotificationUsecase, times(1)).sendNotification(sendNotificationEvent);

    }
}