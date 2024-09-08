package com.phishing.userservice.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserCertification {
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    public static UserCertification create(String email, String password) {
        return UserCertification.builder()
                .email(email)
                .password(password)
                .build();
    }
}
