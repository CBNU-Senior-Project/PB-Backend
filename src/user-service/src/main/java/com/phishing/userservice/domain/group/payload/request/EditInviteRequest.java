package com.phishing.userservice.domain.group.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditInviteRequest {
    private String status; // 상태를 나타내는 필드
}
