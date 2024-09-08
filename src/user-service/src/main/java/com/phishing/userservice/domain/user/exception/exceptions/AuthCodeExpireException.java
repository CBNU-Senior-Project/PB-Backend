package com.phishing.userservice.domain.user.exception.exceptions;

public class AuthCodeExpireException extends RuntimeException{
    public AuthCodeExpireException(String message) {
        super(message);
    }
}
