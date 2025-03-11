package com.phishing.notiservice.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class NotiPayload {
    private String title;
    private String message;

    public static NotiPayload createPredFinNoti(String probablity) {
        return NotiPayload.builder()
                .title("그룹원중 보이스피싱 의심 전화 감지")
                .message("그룹원 중 누군가 보이스피싱 의심 전화를 받았습니다.")
                .build();
    }
}
