package com.phishing.userservice.domain.phish.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phishing.common.payload.Passport;
import com.phishing.userservice.domain.phish.domain.PhishingType;
import com.phishing.userservice.domain.phish.payload.request.PhishingRequest;
import com.phishing.userservice.domain.phish.payload.request.SearchPhishingRequest;
import com.phishing.userservice.domain.phish.payload.response.PhishingResponse;
import com.phishing.userservice.domain.phish.payload.response.SearchPhishingResponse;
import com.phishing.userservice.domain.phish.service.PhishingService;
import com.phishing.userservice.global.component.token.TokenResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/api/v1/phish")
@RequiredArgsConstructor
public class PhishingController {
    private final PhishingService phishingService;
   //private final TokenResolver tokenResolver;
    private final ObjectMapper objectMapper;

    @Tag(name = "피싱 데이터 추가", description = "피싱 계좌, 번호, URL을 추가하는 API")
    @PostMapping("/add")
    public ResponseEntity<Void> addPhishingData(
            @RequestHeader("X-Authorization") String token,
            @RequestBody PhishingRequest request) throws JsonProcessingException {
        Passport passport = objectMapper.readValue(token, Passport.class);
       Long userId = passport.userId();
        //Long userId = tokenResolver.getAccessClaims(token);
        phishingService.addPhishingData(userId, request.getPhishingType(), request.getValue(),request.getContent());
        return ResponseEntity.ok().build();
    }

    @Tag(name = "사기계좌or사기번호or사기url 데이터 전체조회", description = "피싱 데이터타입을 입력받으면 해당 타입 데이터 전체출력하는 API")
    @GetMapping("/data")
    public ResponseEntity<List<PhishingResponse>> getPhishingDataByType(
            @RequestParam("type") PhishingType phishingType) {
        List<PhishingResponse> phishingData = phishingService.getPhishingDataByType(phishingType);
        return ResponseEntity.ok(phishingData);
    }

    @Tag(name = "피싱 데이터 검색", description = "피싱 타입과 값을 입력받아 해당 데이터를 조회하는 API")
    @PostMapping("/search/type-and-value")
    public ResponseEntity<List<PhishingResponse>> searchPhishingDataByTypeAndValue(
            @RequestBody SearchPhishingRequest request) {
        List<PhishingResponse> result = phishingService.searchPhishingDataByTypeAndValue(request.getPhishingType(), request.getValue());
        return ResponseEntity.ok(result);
    }



    @Tag(name = "피싱 데이터 세부사항 조회", description = "피싱 데이터를 조회하여 날짜와 내용을 리스트로 반환하는 API")
    @PostMapping("/detail/search")
    public ResponseEntity<List<SearchPhishingResponse>> searchPhishingData(
            @RequestParam("phishingType") PhishingType phishingType,  // PhishingType을 Enum으로 받음
            @RequestParam("value") String value) {

        // 서비스에서 정확히 일치하는 값을 검색한 결과를 받음
        List<SearchPhishingResponse> result = phishingService.searchPhishingData(phishingType, value);
        return ResponseEntity.ok(result);
    }





    @Tag(name = "피싱 데이터 삭제", description = "피싱 데이터를 삭제하는 API")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhishingData(
            @RequestHeader("X-Authorization") String token,
            @PathVariable("id") Long phishingId) throws JsonProcessingException {

        Passport passport = objectMapper.readValue(token, Passport.class);
        Long userId = passport.userId();
        //Long userId = tokenResolver.getAccessClaims(token);
        phishingService.deletePhishingData(userId, phishingId);
        return ResponseEntity.ok().build();
    }
}
