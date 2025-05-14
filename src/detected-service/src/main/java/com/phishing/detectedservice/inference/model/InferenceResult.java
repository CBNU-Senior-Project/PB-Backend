package com.phishing.detectedservice.inference.model;

import lombok.Builder;

@Builder
public record InferenceResult(
        double probability,
        boolean isPhishing
) {}
