package com.phishing.detectedservice.inference.listener;

import com.phishing.common.event.events.ModelUpdatedEvent;
import com.phishing.detectedservice.inference.model.InferenceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ModelUpdatedEventListener {

    private final InferenceModel inferenceModel;

    @KafkaListener(
            topics = "model-updated",
            groupId = "model-loader",
            containerFactory = "modelUpdatedKafkaListenerContainerFactory"
    )
    public void onModelUpdated(ModelUpdatedEvent event) {
        log.info("모델 업데이트 감지: {}", event.getModelPath());
        inferenceModel.reloadModel(event.getModelPath());
    }
}

