package com.phishing.userservice.domain.news.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record NewsCrawlingResult(
        String title,
        String content,
        String linkUrl,
        String imageUrl
) {
    public static NewsCrawlingResult create(String title, String content, String linkUrl, String imageUrl) {
        return NewsCrawlingResult.builder()
                .title(title)
                .content(content)
                .linkUrl(linkUrl)
                .imageUrl(imageUrl)
                .build();
    }
}
