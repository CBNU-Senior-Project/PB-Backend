package com.phishing.userservice.domain.news.entity;

import com.phishing.userservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "link_url")
    private String linkUrl;

    @Column(name = "image_url")
    private String imageUrl;

    public static News create(String title, String content, String linkUrl, String imageUrl) {
        return News.builder()
                .title(title)
                .content(content)
                .linkUrl(linkUrl)
                .imageUrl(imageUrl)
                .build();
    }
}
