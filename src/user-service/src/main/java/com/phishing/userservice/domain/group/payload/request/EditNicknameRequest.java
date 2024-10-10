package com.phishing.userservice.domain.group.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditNicknameRequest {
    private String nickname;  // 수정된 닉네임
}
