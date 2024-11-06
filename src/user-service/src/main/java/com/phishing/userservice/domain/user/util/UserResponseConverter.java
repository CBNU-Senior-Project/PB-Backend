package com.phishing.userservice.domain.user.util;

import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.payload.response.UserIdResponse;

public class UserResponseConverter {
    public static UserIdResponse from(User user){
        return UserIdResponse.builder()
                .userId(user.getUserId())
                .build();
    }
}
