package com.phishing.userservice.domain.news.controller;

import com.phishing.userservice.domain.news.dto.NewsListResponse;
import com.phishing.userservice.domain.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/api/v1/news")
@RequiredArgsConstructor
@Validated
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/trigger")
    public void triggerNewsCrawling() {
        newsService.createNews();
    }

    @GetMapping("/view")
    public List<NewsListResponse> viewNewsList() {
        return newsService.viewNewsList();
    }

    @DeleteMapping("/delete")
    public void deleteNews() {
        newsService.deleteNews();
    }
}
