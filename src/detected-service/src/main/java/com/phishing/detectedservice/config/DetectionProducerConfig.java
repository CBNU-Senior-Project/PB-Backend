//package com.phishing.detectedservice.config;
//
//import com.phishing.common.event.events.DlqMessage;
//import com.phishing.common.event.events.PhishingDetectedEvent;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.Map;
//
//@Configuration
//public class DetectionProducerConfig {
//
//    private final Map<String, Object> baseConfigs;
//
//    public DetectionProducerConfig(Map<String, Object> baseConfigs) {
//        this.baseConfigs = baseConfigs;
//    }
//
//    @Bean
//    public ProducerFactory<String, PhishingDetectedEvent> phishingDetectedProducerFactory() {
//        return new DefaultKafkaProducerFactory<>(baseConfigs);
//    }
//
//    @Bean
//    public KafkaTemplate<String, PhishingDetectedEvent> phishingDetectedKafkaTemplate() {
//        return new KafkaTemplate<>(phishingDetectedProducerFactory());
//    }
//
//    // 🎯 DlqMessage용
//    @Bean
//    public ProducerFactory<String, DlqMessage> dlqProducerFactory() {
//        return new DefaultKafkaProducerFactory<>(baseConfigs);
//    }
//
//    @Bean
//    public KafkaTemplate<String, DlqMessage> dlqKafkaTemplate() {
//        return new KafkaTemplate<>(dlqProducerFactory());
//    }
//}
