package com.phishing.userservice.domain.phish.domain;

import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "phishing")
public class Phishing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phishing_id")
    private Long phishingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "phishing_type", nullable = false)
    private PhishingType phishingType;

    @Column(name = "value", nullable = false)
    private String value; // 피싱 계좌, 번호, URL 값을 저장하는 필드

    @Column(name = "content", nullable = false)
    private String content; // 피싱 계좌, 번호, URL 값을 저장하는 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;  // 생성자 (User 객체)

    public static Phishing create(User creator, PhishingType phishingType, String value, String content) {
        return Phishing.builder()
                .creator(creator)
                .phishingType(phishingType)
                .value(value)
                .content(content)
                .build();
    }
}