package com.phishing.gatewayservice.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListUri {
    SIGNUP_URI("/user/api/v1/user/signup"),
    SIGNIN_URI("/user/api/v1/auth/signin"),
    TOKEN_REFRESH_URI("/user/api/v1/auth/refresh"),
    CHECK_EMAIL_URI("/user/api/v1/user/users/check"),
    SEND_MAIL_URI("/user/api/v1/email/send"),
    VERIFY_MAIL_URI("/user/api/v1/email/verify"),

    ;
    final String uri;
}
