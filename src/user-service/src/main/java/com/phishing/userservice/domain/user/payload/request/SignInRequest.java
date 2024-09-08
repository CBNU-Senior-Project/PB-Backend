package com.phishing.userservice.domain.user.payload.request;

import com.phishing.userservice.domain.user.domain.UserCertification;

public record SignInRequest(
    UserCertification userCertification
) {
}
