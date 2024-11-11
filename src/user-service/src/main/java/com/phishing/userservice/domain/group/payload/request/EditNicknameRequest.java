package com.phishing.userservice.domain.group.payload.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditNicknameRequest {
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,15}$")
    private String nickname;  // 수정된 닉네임
}
