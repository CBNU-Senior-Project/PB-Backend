package com.phishing.common.config;

import com.phishing.common.event.KafkaEvent;
import com.phishing.common.event.events.DlqMessage;
import com.phishing.common.event.events.PhishingDetectedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap.servers}")
    private String bootstrapServers;

//    @Bean
//    public ProducerFactory<String, KafkaEvent> factory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
//
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    public KafkaTemplate<String, KafkaEvent> kafkaTemplate() {
//        return new KafkaTemplate<>(factory());
//    }

    // 공통 설정
    private Map<String, Object> baseConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        return props;
    }

    // PhishingDetectedEvent용
    @Bean
    public ProducerFactory<String, PhishingDetectedEvent> phishingDetectedProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseConfigs());
    }

    @Bean
    public KafkaTemplate<String, PhishingDetectedEvent> phishingDetectedKafkaTemplate() {
        return new KafkaTemplate<>(phishingDetectedProducerFactory());
    }

    // DlqMessage용
    @Bean
    public ProducerFactory<String, DlqMessage> dlqProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseConfigs());
    }

    @Bean
    public KafkaTemplate<String, DlqMessage> dlqKafkaTemplate() {
        return new KafkaTemplate<>(dlqProducerFactory());
    }

    // modelUpdatedEvent용
    @Bean
    public ProducerFactory<String, KafkaEvent> modelUpdatedProducerFactory() {
        Map<String, Object> props = new HashMap<>(baseConfigs());
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // DLQ 재전송 시 권장
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, KafkaEvent> modelUpdatedKafkaTemplate() {
        return new KafkaTemplate<>(modelUpdatedProducerFactory());
    }

}
