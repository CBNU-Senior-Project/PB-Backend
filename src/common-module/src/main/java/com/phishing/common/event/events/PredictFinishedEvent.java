package com.phishing.common.event.events;

import com.phishing.common.event.EventType;
import com.phishing.common.event.KafkaEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictFinishedEvent implements KafkaEvent {

    private Long userId;
    private boolean isPhishing;
    private String probability;

    @Override
    public String getEventType() {
        return EventType.PREDICT_FINISHED.getValue();
    }
}
