package com.phishing.gatewayservice.payload;

public record Passport(
    String email,
    String nickname,
    String role
) {
}
