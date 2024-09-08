package com.phishing.userservice.domain.user.exception.exceptions;

public class FailSendEmailException extends RuntimeException{
    public FailSendEmailException(String message) {
        super(message);
    }
}
