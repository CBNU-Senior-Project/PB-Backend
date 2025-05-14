package com.phishing.detectedservice.inference.model;

public interface InferenceModel {
    InferenceResult predict(String input);
    void reloadModel(String modelPath);
}
