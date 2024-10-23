package com.phishing.userservice.domain.news.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhishingNewsCrawlerTest {

    private final PhishingNewsCrawler phishingNewsCrawler;

    PhishingNewsCrawlerTest(@Autowired PhishingNewsCrawler phishingNewsCrawler) {
        this.phishingNewsCrawler = phishingNewsCrawler;
    }

    @Test
    public void crawlerTest(){
        phishingNewsCrawler.crawlNews();
    }
}