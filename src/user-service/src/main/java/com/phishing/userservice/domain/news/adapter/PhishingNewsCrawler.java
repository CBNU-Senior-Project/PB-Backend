package com.phishing.userservice.domain.news.adapter;

import com.phishing.userservice.domain.news.config.WebDriverConfig;
import com.phishing.userservice.domain.news.dto.NewsCrawlingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PhishingNewsCrawler {

    private final WebDriverConfig webDriverConfig;

    @Value("${news.crawling.baseurl}")
    private String NEWS_CRAWLING_URL;

    public List<NewsCrawlingResult> crawlNews() {
        WebDriver webDriver = webDriverConfig.webDriver();
        List<NewsCrawlingResult> results = new ArrayList<>();
        try{
            log.info("Start crawling news");
            webDriver.get(NEWS_CRAWLING_URL);

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.news_tit")));

            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("window.scrollBy(0,1500)", "");

            List<WebElement> titleElements = webDriver.findElements(By.cssSelector("a.news_tit"));
            List<WebElement> contentElements = webDriver.findElements(By.cssSelector("a.api_txt_lines"));
            List<WebElement> imageElements = webDriver.findElements(By.cssSelector("img.thumb"));

            for (int i=0; i< titleElements.size(); i++){
                String title = titleElements.get(i).getText();
                String content = contentElements.get(i).getText();
                String linkUrl = titleElements.get(i).getAttribute("href");
                String imageUrl = imageElements.get(2*i+1).getAttribute("src");
                NewsCrawlingResult crawlingResult = NewsCrawlingResult.create(title, content, linkUrl, imageUrl);

                results.add(crawlingResult);
            }

            return results;

        } catch (Exception e){
            log.error("Crawling Failure: " + e.getMessage());
        } finally {
            webDriver.quit();
        }

        return null;
    }
}
