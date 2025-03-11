package com.phishing.userservice.domain.news.service;

import com.phishing.userservice.domain.news.adapter.PhishingNewsCrawler;
import com.phishing.userservice.domain.news.dto.NewsCrawlingResult;
import com.phishing.userservice.domain.news.dto.NewsListResponse;
import com.phishing.userservice.domain.news.entity.News;
import com.phishing.userservice.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final PhishingNewsCrawler phishingNewsCrawler;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *") //
    public void createNews() {
        try {
            List<NewsCrawlingResult> results = phishingNewsCrawler.crawlNews();
            results.forEach(result -> {
                newsRepository.save(
                        News.create(
                                result.title(),
                                result.content(),
                                result.linkUrl(),
                                result.imageUrl()
                        )
                );
            });
        } catch (Exception e) {
            log.error("News Crawling Saving Failure: " + e.getMessage());
        }

    }

    public List<NewsListResponse> viewNewsList() {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream()
                .map(NewsListResponse::from)
                .toList();
    }

    public List<NewsListResponse> viewNewsListAfterYesterday() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        List<News> newsList = newsRepository.findAllByAfterYesterday(yesterday);
        return newsList.stream()
                .map(NewsListResponse::from)
                .toList();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *") //
    public void deleteNews() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        newsRepository.deleteByCreatedAtBefore(yesterday);
    }
}
