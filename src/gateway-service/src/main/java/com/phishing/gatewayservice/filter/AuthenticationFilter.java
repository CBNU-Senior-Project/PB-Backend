package com.phishing.gatewayservice.filter;

import com.phishing.gatewayservice.external.PassportClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final PassportClient passportClient;

    public AuthenticationFilter(@Qualifier("passportClient") PassportClient passportClient) {
        super(Config.class);
        this.passportClient = passportClient;
    }

    public static class Config{}

    @Override
    public GatewayFilter apply (Config config){
        return (exchange, chain) -> {
            String uri = exchange.getRequest().getURI().getPath();

            if (isWhiteListed(uri)){
                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey("Authorization")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = exchange.getRequest().getHeaders().get("Authorization").get(0);

            // RestClient 사용
            String passport = passportClient.generatePassport(token);

            ServerHttpRequest request = exchange.getRequest().mutate()
                                .header("X-Authorization", passport)
                                .build();
            return chain.filter(exchange.mutate().request(request).build());

//            // open feign 사용
//            return passportClient.generatePassport(token)
//                    .flatMap(response -> {
//                        String passport = response.getBody();
//                        log.debug("Passport: {}", passport);
//                        ServerHttpRequest request = exchange.getRequest().mutate()
//                                .header("X-Authorization", passport)
//                                .build();
//                        return chain.filter(exchange.mutate().request(request).build());
//                    });
        };
    }

    private boolean isWhiteListed(String uri){
        for (WhiteListUri whiteListedUri : WhiteListUri.values()){
            if (whiteListedUri.uri.equals(uri)){
                return true;
            }
        }
        return false;
    }

}
