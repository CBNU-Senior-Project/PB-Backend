package com.phishing.common.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    PREDICT_FINISHED("predict-finished"),
    PREDICT_FAILED("predict-failed"),
    DEAD_LETTER("dead-letter"),
    MODEL_UPDATED("model-updated");

    private final String value;

    public String getValue() {
        return value;
    }
}
