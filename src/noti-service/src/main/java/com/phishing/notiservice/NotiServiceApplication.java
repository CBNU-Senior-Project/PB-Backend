package com.phishing.notiservice;

import com.phishing.common.config.KafkaConsumerConfig;
import com.phishing.common.config.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
		KafkaProducerConfig.class,
		KafkaConsumerConfig.class
})
public class NotiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotiServiceApplication.class, args);
	}

}
