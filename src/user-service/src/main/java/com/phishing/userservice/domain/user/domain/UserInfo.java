package com.phishing.userservice.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Column(name = "phnum", nullable = false, length = 20)
    private String phnum;
}
