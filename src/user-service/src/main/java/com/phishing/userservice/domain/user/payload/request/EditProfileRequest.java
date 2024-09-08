package com.phishing.userservice.domain.user.payload.request;

import com.phishing.userservice.domain.user.domain.UserInfo;

import java.util.List;

public record EditProfileRequest(
        UserInfo userInfo
) {
}
