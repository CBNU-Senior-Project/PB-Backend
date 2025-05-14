package com.phishing.common.event.events;

import com.phishing.common.event.EventType;
import com.phishing.common.event.KafkaEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ModelUpdatedEvent implements KafkaEvent {
    private String modelPath;
    private LocalDateTime updatedAt;

    @Override
    public String getEventType() {
        return EventType.MODEL_UPDATED.getValue();
    }
}

