package com.phishing.userservice.domain.phish.controller;

import com.phishing.userservice.domain.phish.domain.PhishingType;
import com.phishing.userservice.domain.phish.payload.request.PhishingRequest;
import com.phishing.userservice.domain.phish.payload.response.PhishingResponse;
import com.phishing.userservice.domain.phish.service.PhishingService;
import com.phishing.userservice.global.component.token.TokenResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phish/api/v1")
@RequiredArgsConstructor
public class PhishingController {
    private final PhishingService phishingService;
    private final TokenResolver tokenResolver;

    @Tag(name = "피싱 데이터 추가", description = "피싱 계좌, 번호, URL을 추가하는 API")
    @PostMapping("/add")
    public ResponseEntity<Void> addPhishingData(
            @RequestHeader("Authorization") String token,
            @RequestBody PhishingRequest request) {
        Long userId = tokenResolver.getAccessClaims(token);
        phishingService.addPhishingData(userId, request.getPhishingType(), request.getValue());
        return ResponseEntity.ok().build();
    }

    @Tag(name = "피싱 데이터 조회", description = "피싱 데이터를 조회하는 API")
    @GetMapping("/data")
    public ResponseEntity<List<PhishingResponse>> getPhishingDataByType(
            @RequestParam("type") PhishingType phishingType) {
        List<PhishingResponse> phishingData = phishingService.getPhishingDataByType(phishingType);
        return ResponseEntity.ok(phishingData);
    }

    @Tag(name = "피싱 데이터 검색", description = "피싱 데이터를 검색하는 API")
    @PostMapping("/search")
    public ResponseEntity<List<PhishingResponse>> searchPhishingData(
            @RequestBody PhishingRequest request) {
        List<PhishingResponse> results = phishingService.searchPhishingData(request.getPhishingType(), request.getValue());
        return ResponseEntity.ok(results);
    }

    @Tag(name = "피싱 데이터 삭제", description = "피싱 데이터를 삭제하는 API")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhishingData(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") Long phishingId) {
        Long userId = tokenResolver.getAccessClaims(token);
        phishingService.deletePhishingData(userId, phishingId);
        return ResponseEntity.ok().build();
    }
}
