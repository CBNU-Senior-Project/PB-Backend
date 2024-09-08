//package com.phishing.gatewayservice.config;
//
//import com.phishing.gatewayservice.external.PassportClient;
//import feign.Logger;
//import feign.form.spring.SpringFormEncoder;
//import feign.gson.GsonDecoder;
//import feign.gson.GsonEncoder;
//import feign.reactive.ReactorDecoder;
//import feign.reactive.ReactorFeign;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.cloud.openfeign.support.SpringDecoder;
//import org.springframework.cloud.openfeign.support.SpringEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//// open feign config
//@Configuration
//public class OpenFeignConfig {
//
//    private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;
//
//    @Bean
//    public PassportClient passportClient() {
//        return ReactorFeign.builder()
//                .logLevel(Logger.Level.FULL)
//                .encoder(new GsonEncoder())
//                .decoder(new ReactorDecoder(new GsonDecoder()))
//                .target(PassportClient.class, "http://localhost:8082");
//    }
//
//    @Bean
//    public SpringFormEncoder feignFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(messageConverters));
//    }
//
//    @Bean
//    public SpringDecoder feignFormDecoder() {
//        return new SpringDecoder(messageConverters);
//    }
//
//}
