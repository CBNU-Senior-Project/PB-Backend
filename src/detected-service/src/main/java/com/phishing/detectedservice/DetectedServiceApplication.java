package com.phishing.detectedservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.phishing.detectedservice",
		"com.phishing.common"
})
public class DetectedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectedServiceApplication.class, args);
	}

}
