package com.phishing.userservice.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Column(name = "nickname", nullable = false, length = 15)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,15}$")
    private String nickname;

    @Column(name = "phnum", nullable = false, length = 20)
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phnum;
}
