package com.phishing.gatewayservice.external;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

//// open feign client
//@Component
//public interface PassportClient {
//    @RequestLine("POST /auth/api/v1/passport")
//    @Headers("Authorization: {token}")
//    public Mono<ResponseEntity<String>> generatePassport(@Param("token") String token);
//}

@Component
@Qualifier("passportClient")
public class PassportClient {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final RestClient restClient = RestClient.create();

    @Value("${restclient.baseuri}")
    private String baseUri;

    public String generatePassport(String token) {
        return restClient
                .post()
                .uri(baseUri + "/user/api/v1/auth/passport")
                .header(AUTHORIZATION_HEADER_NAME, token)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}