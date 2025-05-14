package com.phishing.common.event.events;

import com.phishing.common.event.EventType;
import com.phishing.common.event.KafkaEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhishingDetectedEvent implements KafkaEvent {
    private String detectionId;
    private Long userId;
    private String text;
    private double probability;
    private boolean isPhishing;
    private LocalDateTime detectedAt;

    @Override
    public String getEventType() {
        return EventType.PREDICT_FINISHED.getValue();
    }
}
