package com.phishing.userservice.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserCertification {
    @Column(name = "email", nullable = false, length = 50)
    @Email
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:'\",<>.?\\/]).{8,20}$")
    private String password;

    public static UserCertification create(String email, String password) {
        return UserCertification.builder()
                .email(email)
                .password(password)
                .build();
    }
}
