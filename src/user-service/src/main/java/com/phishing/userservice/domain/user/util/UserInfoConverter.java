package com.phishing.userservice.domain.user.util;

import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.payload.response.UserInfoResponse;

public class UserInfoConverter {
    public static UserInfoResponse from(User user){
        return UserInfoResponse.builder()
                .userInfo(user.getUserInfo())
                .build();
    }
}
