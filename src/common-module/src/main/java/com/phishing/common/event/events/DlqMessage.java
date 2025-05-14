package com.phishing.common.event.events;

import com.phishing.common.event.EventType;
import com.phishing.common.event.KafkaEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DlqMessage implements KafkaEvent {
    private KafkaEvent originalMessage;
    private String sourceTopic;
    private LocalDateTime failedAt;
    private String errorMessage;
    private int retryCount;


    @Override
    public String getEventType() {
        return EventType.DEAD_LETTER.getValue();
    }
}
