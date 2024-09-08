package com.phishing.userservice.domain.user.payload.request;

public record VerifyEmailRequest(
        String email,
        String authCode
) {
}
