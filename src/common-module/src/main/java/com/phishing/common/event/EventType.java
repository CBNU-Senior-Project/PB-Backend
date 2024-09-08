package com.phishing.common.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    PREDICT_FINISHED("predict-finished"),
    PREDICT_FAILED("predict-failed");

    private final String value;

    public String getValue() {
        return value;
    }
}
