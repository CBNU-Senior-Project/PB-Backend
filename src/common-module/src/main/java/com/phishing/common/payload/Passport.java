package com.phishing.common.payload;

public record Passport (
        Long userId,
        String email,
        String nickname,
        String role
){
}