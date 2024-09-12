package com.phishing.notiservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.phishing.common", "com.phishing.notiservice"})
public class NotiServiceConfig {
}
