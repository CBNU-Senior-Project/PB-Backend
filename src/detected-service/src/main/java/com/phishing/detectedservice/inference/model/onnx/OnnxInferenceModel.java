package com.phishing.detectedservice.inference.model.onnx;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import com.phishing.detectedservice.inference.model.InferenceModel;
import com.phishing.detectedservice.inference.model.InferenceResult;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class OnnxInferenceModel implements InferenceModel {

    private OrtEnvironment env;
    private OrtSession session;

    @Value("${onnx.model.path}")
    private String modelPath;

    @PostConstruct
    public void init() {
        this.env = OrtEnvironment.getEnvironment();
        reloadModel(modelPath);
    }

    @Override
    public InferenceResult predict(String input) {
        try {
            OnnxTensor tensor = OnnxTensor.createTensor(env, new String[]{input});
            OrtSession.Result result = session.run(Map.of("input", tensor));
            float[][] output = (float[][]) result.get(0).getValue();
            float prob = output[0][1];
            return new InferenceResult(prob, prob > 0.5);
        } catch (Exception e) {
            throw new RuntimeException("추론 실패", e);
        }
    }

    @Override
    public void reloadModel(String path) {
        ModelReloadLock.lock(() -> {
            OrtSession oldSession = this.session;
            OrtSession newSession = null;

            try {
                newSession = env.createSession(path, new OrtSession.SessionOptions());
                this.session = newSession;
                log.info("ONNX 모델 교체 성공: {}", path);
            } catch (Exception e) {
                log.error("ONNX 모델 로딩 실패: {} - 기존 세션 유지", path, e);
                if (newSession != null) {
                    try {
                        newSession.close();
                    } catch (Exception closeEx) {
                        log.warn("실패한 새 세션 close 중 오류", closeEx);
                    }
                }
            } finally {
                if (oldSession != null && oldSession != this.session) {
                    try {
                        oldSession.close();
                    } catch (Exception e) {
                        log.warn("이전 세션 종료 실패", e);
                    }
                }
            }
        });
    }

}

