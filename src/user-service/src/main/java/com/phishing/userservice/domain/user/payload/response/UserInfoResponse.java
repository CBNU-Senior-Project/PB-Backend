package com.phishing.userservice.domain.user.payload.response;
import com.phishing.userservice.domain.user.domain.UserInfo;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        UserInfo userInfo
) {
}
