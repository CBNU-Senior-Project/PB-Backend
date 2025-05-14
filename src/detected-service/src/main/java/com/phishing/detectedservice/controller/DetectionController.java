package com.phishing.detectedservice.controller;

import com.phishing.detectedservice.dto.event.PhishingDetectedSpringEvent;
import com.phishing.detectedservice.service.DetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detection/api/v1")
public class DetectionController {

//    private final DetectionService detectionService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestBody PhishingDetectedSpringEvent event){
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok().build();
    }

}
