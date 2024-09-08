package com.phishing.userservice.domain.user.payload.request;

import com.phishing.userservice.domain.user.domain.UserCertification;
import com.phishing.userservice.domain.user.domain.UserInfo;
import com.phishing.userservice.domain.user.domain.UserRole;

import java.util.List;

public record SignUpRequest(
        UserCertification userCertification,
        UserInfo userInfo,
        UserRole userRole
) {
}
