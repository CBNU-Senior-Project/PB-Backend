package com.phishing.userservice.domain.news.dto;

import com.phishing.userservice.domain.news.entity.News;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record NewsListResponse(
        String title,
        String content,
        String linkUrl,
        String imageUrl
) {
    public static NewsListResponse from(News news) {
        return NewsListResponse.builder()
                .title(news.getTitle())
                .content(news.getContent())
                .linkUrl(news.getLinkUrl())
                .imageUrl(news.getImageUrl())
                .build();
    }
}
