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
                .title("탐지결과 알림")
                .message("해당 전화의 보이스피싱 가능성은 " + probablity + "% 입니다.")
                .build();
    }
}
