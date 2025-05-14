package com.phishing.detectedservice.inference.model.onnx;

import java.util.concurrent.locks.ReentrantLock;

public class ModelReloadLock {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void lock(Runnable action) {
        lock.lock();
        try {
            action.run();
        } finally {
            lock.unlock();
        }
    }
}
